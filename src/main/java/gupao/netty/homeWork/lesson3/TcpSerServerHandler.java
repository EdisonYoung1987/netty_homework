package gupao.netty.homeWork.lesson3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TcpSerServerHandler extends ChannelInboundHandlerAdapter{ //inbound和outbound什么区别

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { //channel激活
//		super.channelActive(ctx);
		System.out.println("通道激活");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {//channel读
		System.out.println("server recv message:"+msg);
//		super.channelRead(ctx, msg);
		ctx.channel().writeAndFlush("accepte message:"+msg+"  hello");
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {//异常处理
//		super.exceptionCaught(ctx, cause);
		System.out.println("catch exc："+cause.getLocalizedMessage());
	}
}
