package gupao.netty.homeWork.lesson4;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
// Sharable 主要是为了多个handler可以被多个channel安全地共享，也就是保证线程安全
@Sharable 
public class ClientHandler extends SimpleChannelInboundHandler<String> {
	// 打印读取到的数据
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg)
			throws Exception {
		System.err.println(msg);
	}

	// 异常数据捕获
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
