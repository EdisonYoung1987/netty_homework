package gupao.netty.homeWork.lesson3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class NettyClient implements Runnable {

	@Override
	public void run() {
		EventLoopGroup group=new NioEventLoopGroup(100);
		try{
			Bootstrap bootstrap=new Bootstrap(); //netty组装好的一个客户端启动器框架，用户只需要把各个零件插上去就行
			bootstrap.group(group);              //多线程reactor模型的reactor
			bootstrap.channel(NioSocketChannel.class); //调用ReflectiveChannelFactory.newChannel(z) 反射创建实例
			bootstrap.option(ChannelOption.TCP_NODELAY, true);//Map<ChannelOption<?>, Object> options的配置
			
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {//初始化-一个特殊的ChannelInboundHandlerAdapter抽象类，
													//通过该初始化器可以将用户自定义的ChannelHandler对象加入到处理器链中
						@Override
						protected void initChannel(SocketChannel sc) throws Exception {
							ChannelPipeline channelPipeline=sc.pipeline(); //负责处理或拦截输入事件和输出操作的处理器链表
							
							//自定义长度解码器  发送最大帧数 - 定义长度域位于发送的字节数组中的下标 - 长度域的长度
							channelPipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4));
							
							channelPipeline.addLast("frameDecoder",new LengthFieldPrepender(4))
								.addLast("StringDecoder",new StringDecoder(CharsetUtil.UTF_8))
							    .addLast("StringEncoder",new StringEncoder(CharsetUtil.UTF_8))
						        .addLast("handler",new Myclient());
						}

						
					});
			
			for(int i=0;i<100;i++){
				ChannelFuture future=bootstrap.connect("127.0.0.1",8888).sync();
				future.channel().writeAndFlush("hello "+Thread.currentThread().getName()+""+i);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		for(int i=0;i<3;i++){
			new Thread(new NettyClient(),"this is thread "+i).start();
		}
	}

}
