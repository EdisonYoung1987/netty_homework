package gupao.netty.selfStudy.A_BootStrap;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<String>{

	/**
	 *此方法会在连接到服务器后被调用 
	 * */
	public void channelActive(ChannelHandlerContext ctx) {
		System.out.println("channelActive");
		ctx.writeAndFlush("你好 ，服务端");
		try {
			System.out.println("睡眠10s后再读取数据");
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 *此方法会在接收到服务器数据后调用 
	 * */
	public void channelRead0(ChannelHandlerContext ctx, String in) {
		System.out.println("channelRead0: Client received: " +in);
		ctx.close();
	}

	
	/**
	 *捕捉到异常 
	 * */
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
	
	/**以下是测试这些方法的执行顺序*/
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelRegistered");

	}
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelUnregistered");

	}
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelInactive");

	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelReadComplete");

	}
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		System.out.println("userEventTriggered");
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx)
			throws Exception {
		System.out.println("channelWritabilityChanged");

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
