package gupao.netty.homeWork.lesson4;

import gupao.netty.homeWork.lesson4.ServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**这是对SERVER的设置类，设置他的编解码类，设置它的业务逻辑实现类*/
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
	private static final StringDecoder DECODER = new StringDecoder();
	private static final StringEncoder ENCODER = new StringEncoder();
	private static final ServerHandler SERVER_HANDLER = new ServerHandler();

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		// 添加帧限定符来防⽌粘包现象
		pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters
				.lineDelimiter()));
		// 解码和编码，应和客户端⼀致
		pipeline.addLast(DECODER);
		pipeline.addLast(ENCODER);
		// 业务逻辑实现类
		pipeline.addLast(SERVER_HANDLER);
	}
}