package gupao.netty.selfStudy.A_BootStrap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class NettyServer {
	private static final int PORT=8888;
	private static NioEventLoopGroup bossGroup=new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
	private static NioEventLoopGroup workerGroup=new NioEventLoopGroup(100);
	
	public static void main(String[] args) {
		ServerBootstrap serverBootstrap=new ServerBootstrap();
		serverBootstrap.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
//			.handler(xxx)
			.childHandler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					ChannelPipeline pipeline=ch.pipeline();
					pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
					pipeline.addLast(new LengthFieldPrepender(4));
					pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
					pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
					pipeline.addLast(new ServerHandler());
				}
				
			})
			.bind(PORT);
	}

}
