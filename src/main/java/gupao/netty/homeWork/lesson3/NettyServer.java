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
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class NettyServer {

		private static final String IP="127.0.0.1";
		private static final int  PORT=8888;

		private static final int  BIZGROUPSIZE=Runtime.getRuntime().availableProcessors();//线程数
		
		private static final int BIZTHREADSIZE=100;
		
		private static final EventLoopGroup bossGroup=new NioEventLoopGroup(BIZGROUPSIZE);
		private static final EventLoopGroup workGroup=new NioEventLoopGroup(BIZTHREADSIZE);
		
		public static void start() throws Exception {
			ServerBootstrap serverBootstrap=new ServerBootstrap();
			serverBootstrap.group(bossGroup,workGroup)
				.channel(NioServerSocketChannel.class)
				//.handler("") 对于服务器的处理
				.childHandler(new ChannelInitializer<Channel>() { //对于连接的处理
					
					@Override
					protected void initChannel(Channel ch) throws Exception {
						ChannelPipeline pipeline=ch.pipeline();//处理器的双向链表
						pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));
						pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
						pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
						pipeline.addLast(new TcpSerServerHandler());
					}})
				;
			ChannelFuture channelFuture=serverBootstrap.bind(IP,PORT).sync();//初始化channel，设置属性等  newsocket方法 selectorProvider
			channelFuture.channel().closeFuture().sync();
			System.out.println("server start");
		}
		
		protected static void shutdown(){
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
		
		public static void main(String[] args) throws Exception{
			System.out.println("qidong server...");
			NettyServer.start();
		}
}
