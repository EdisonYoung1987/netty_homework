package gupao.netty.homeWork.lesson3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class NettyClient implements Runnable {

	@Override
	public void run() {
		EventLoopGroup group=new NioEventLoopGroup();
		try{
			Bootstrap bootstrap=new Bootstrap();
			bootstrap.group(group);
			bootstrap.channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<Channel>() {

						@Override
						protected void initChannel(Channel ch) throws Exception {
							ChannelPipeline channelPipeline=ch.pipeline();
							channelPipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4))
								.addLast("frameDecoder",new LengthFieldPrepender(4))
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

	private static void main(String[] args) {
		for(int i=0;i<3;i++){
			new Thread(new NettyClient(),"this is thread "+i).start();
		}
	}

}
