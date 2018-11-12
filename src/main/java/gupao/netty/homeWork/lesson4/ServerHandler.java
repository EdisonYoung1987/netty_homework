package gupao.netty.homeWork.lesson4;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.Delimiters;

import java.net.InetAddress;
import java.util.Date;

/**实际Server逻辑处理类*/
public class ServerHandler extends SimpleChannelInboundHandler<String> {
	/**
	 * - 建立连接时，发送一条庆祝消息
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 为新连接发送庆祝
		ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName()
				+ "!/r/n");
		ctx.write("It is " + new Date() + " now./r/n");
		ctx.flush();
	}

	// 业务逻辑处理
	@Override
	public void channelRead0(ChannelHandlerContext ctx, String request)
			throws Exception {
		// Generate and write a response.
		String response;
		boolean close = false;
		if (request.isEmpty()) {
//			response = "Please type something./r/n"+Delimiters.lineDelimiter();
			response = "Please type something./r/n";
		} else if ("bye".equals(request.toLowerCase())) {
			response = "Have a good day!/r/n";
			close = true;
		} else {
			response = "Did you say '" + request + "'?/r/n";
		}
		ChannelFuture future = ctx.write(response);
		if (close) {//关闭
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	// 异常处理
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
