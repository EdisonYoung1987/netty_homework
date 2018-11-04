package gupao.netty.homeWork.lesson2.chatTool;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import gupao.netty.homeWork.lesson1.client.Client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatTool {
	private static final Logger logger = LoggerFactory.getLogger( Client.class );
	
	private static final int DEFAULT_PORT=8888;//客户端监听端口 用于接收消息
	private static final String USERNAME="客户端1";
	
	private CountDownLatch countDownLatch =new CountDownLatch(1);
	
	/**启动*/
	public void start(){
		//1 启动监听进程：接收消息、接收上下线消息并维护用户地址表
		Thread thread=new Thread();
		try {
			countDownLatch.await(10, TimeUnit.SECONDS);//等待10s还没启动就认为失败
		} catch (InterruptedException e) {
			logger.error("客户端启动监听期间出错：{}",e.getLocalizedMessage());
			thread.interrupt();//TODO 这里是不是要尝试停止启动线程
			return;
		}
		logger.info("监听已启动");
		
		//2 发送广播消息到局域网
		
		
		//3 循环读取用户消息并发送出去
	}
	
}
