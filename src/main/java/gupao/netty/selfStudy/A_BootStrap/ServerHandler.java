package gupao.netty.selfStudy.A_BootStrap;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("收到:"+msg);
		ctx.channel().writeAndFlush("你好 客户端");
	}

	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();//某个连接出现异常时要关闭掉。
	}


	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		System.out.println("userEventTriggered()");

	}


	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx)
			throws Exception {
		System.out.println("channelWritabilityChanged()");

	}


	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerAdded()");

	}


	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerRemoved()");

	}

}
