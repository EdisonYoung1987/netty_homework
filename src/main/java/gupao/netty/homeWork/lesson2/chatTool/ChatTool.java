package gupao.netty.homeWork.lesson2.chatTool;

import gupao.netty.homeWork.lesson2.base.UserInfo;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatTool {
	private static final Logger logger = LoggerFactory.getLogger( ChatTool.class );
	private CountDownLatch countDownLatch =new CountDownLatch(1); //启动监听时使用
	
	private static final String IP="127.0.0.1";
	private int PORT; //监听端口
	private String USERNAME; //用户名
	public List<UserInfo> userlist;//在线好友列表
	
	public ChatTool(String userName,int port){
		USERNAME=userName;
		PORT=port;
		userlist=new ArrayList<UserInfo>();
	}
	
	/**启动*/
	public void start(){
		//1 启动监听进程：NIO方式 接收消息、接收上下线消息并维护用户地址表
		ChatTool_Recv chatTool_Recv=new ChatTool_Recv(PORT,this.userlist);
		Thread listen_thread=new Thread(chatTool_Recv);
		listen_thread.start();
		try {
			countDownLatch.await(10, TimeUnit.SECONDS);//等待10s还没启动就认为失败
		} catch (InterruptedException e) {
			logger.error("客户端启动监听期间出错：{}",e.getLocalizedMessage());
			listen_thread.interrupt();//TODO 这里是不是要尝试停止启动线程
			return;
		}
		logger.info("监听已启动");
		logger.info("本机：{}  端口{}",USERNAME,PORT);
		
		//2 发送广播消息到局域网
		ChatTool_Send chatTool_Send=new ChatTool_Send(this.userlist);
		chatTool_Send.send_broadCast(0);//广播上线消息
		
		//3 循环读取用户消息并发送出去
		do{
			System.out.println("\n当前用户列表："+this.userlist);
			System.out.println("请输入要发送的对象以及消息信息,格式：用户昵称(ip:prot)|消息信息 (点击回车发送,QUIT退出)");
			Scanner scanner=new Scanner(System.in);
			String content=scanner.nextLine();
			
			if("QUIT".equals(content)){
				break;
			}
			
			UserInfo myself=new UserInfo();
			myself.setIp(IP);
			myself.setPort(PORT);
			myself.setName(USERNAME);
			if(!chatTool_Send.send_single(content,myself)){//TODO 暂时还没完成
				logger.error("发送消息失败");
			}
			
		}while(true);
		
		//程序退出
		listen_thread.interrupt(); //尝试停止监听线程
	}
	
	public List<UserInfo> getUserList(){
		return this.userlist;
	}
	
}
