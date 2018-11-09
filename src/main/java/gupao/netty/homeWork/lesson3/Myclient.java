package gupao.netty.homeWork.lesson3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class Myclient extends ChannelInboundHandlerAdapter{

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
	}

	//TODO 客户端如何退出？
	//TODO 客户端读写顺序怎么定的？
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("channelRead");

		System.out.println("client recv msg="+msg);
		ctx.close();
		ctx.channel().close();
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx)//进入下一个handler处理？如果有的话
			throws Exception {
		System.out.println("channelWritabilityChanged"); 
		super.channelWritabilityChanged(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		System.err.println("client exc"+cause.getLocalizedMessage());
	}

}
