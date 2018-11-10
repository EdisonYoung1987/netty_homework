package gupao.netty.homeWork.lesson3;

import java.nio.charset.Charset;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class NettyServer {

		private static final String IP="127.0.0.1";
		private static final int  PORT=8888;

		private static final int  BIZGROUPSIZE=Runtime.getRuntime().availableProcessors();//获取cpu核数
		
		private static final int BIZTHREADSIZE=100; //每个线程组workergroup拥有的线程数
		
		private static final EventLoopGroup bossGroup=new NioEventLoopGroup(BIZGROUPSIZE);//专门处理连接请求
		private static final EventLoopGroup workGroup=new NioEventLoopGroup(BIZTHREADSIZE);//专门处理数据的业务逻辑
		
		public static void start() throws InterruptedException  {
			ServerBootstrap serverBootstrap=new ServerBootstrap();
			serverBootstrap.group(bossGroup,workGroup)
				.channel(NioServerSocketChannel.class)
				//.handler("") 对于服务器的处理 boss相关的处理器，比如可以打印日志。
				.childHandler(new ChannelInitializer<Channel>() { //对于连接的处理，即workergroup的处理
					
					@Override
					protected void initChannel(Channel ch) throws Exception {
						ChannelPipeline pipeline=ch.pipeline();//处理器的双向链表
						//第四个参数定义的是跳过报文的长度，这里是4，就跳过了前面4字节的长度内容
						pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4,0,4));
						pipeline.addLast(new LengthFieldPrepender(4));
						pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
						pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
						pipeline.addLast(new TcpSerServerHandler());
					}})
				;
			ChannelFuture channelFuture=serverBootstrap.bind(IP,PORT).sync();//初始化channel，设置属性等  newsocket方法 selectorProvider ChannelFuture#sync() 等待阻塞成功
			channelFuture.channel().closeFuture().sync(); // ChannelFuture是用来监听服务器不再监听端口，即服务器已停止。
			System.out.println("server start");
		}
		
		protected static void shutdown(){//怎么钩？
			System.out.println("优雅的退出？？");
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
		
		public static void main(String[] args) {
			System.out.println("qidong server...");
			try{
				NettyServer.start();
			}catch(InterruptedException e){
				System.out.println("SERVER 被打断");
			}finally{
				System.out.println("其他异常？ 比如端口已被监听");
				NettyServer.shutdown();
			}
		}
}
