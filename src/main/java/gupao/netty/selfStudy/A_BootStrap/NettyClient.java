package gupao.netty.selfStudy.A_BootStrap;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
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

import java.net.InetSocketAddress;

public class NettyClient {
	private final String host;
	private final int port;

	public NettyClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void start() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group);
			b.channel(NioSocketChannel.class);
			b.remoteAddress(new InetSocketAddress(host, port));
			b.handler(new ChannelInitializer<SocketChannel>() {

				public void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline channelPipeline=ch.pipeline();
					channelPipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
					channelPipeline.addLast("frameDecoder",new LengthFieldPrepender(4,false));//编码 消息添加4字节的长度内容 false-不包含报文长度内容的长度
					
					//字节码到String的转换
					channelPipeline.addLast("StringDecoder",new StringDecoder(CharsetUtil.UTF_8));
					channelPipeline.addLast("StringEncoder",new StringEncoder(CharsetUtil.UTF_8));
					channelPipeline.addLast(new ClientHandler());
				}
			});
			ChannelFuture f = b.connect().sync();
			f.addListener(new ChannelFutureListener() {
				
				public void operationComplete(ChannelFuture future) throws Exception {
					if(future.isSuccess()){
						System.out.println("client connected");
					}else{
						System.out.println("server attemp failed");
						future.cause().printStackTrace();
					}
					
				}
			});
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}
	}

	public static void main(String[] args) throws Exception {
	
		new NettyClient("127.0.0.1", 8888).start();
	}
}