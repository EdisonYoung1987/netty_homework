package gupao.netty.homeWork.lesson1.client;

import gupao.netty.homeWork.lesson1.base.Message;
import gupao.netty.homeWork.lesson1.util.ConnectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**客户端：启动两个线程，一个用于注册/登录用户信息到服务器并循环监听服务器转发的消息,<br>
 * 					 另一个线程用于循环读取用户输入信息并发送给服务器*/
public class Client2 {
	private static final Logger logger = LoggerFactory.getLogger( Client2.class );

	private static final int DEFAULT_SERVER_PORT=7777; //服务端默认地址
	private static final String DEFAULT_SERVER_IP="127.0.0.1"; //默认端口号
	
	private static final String DEFAULT_IP="127.0.0.1"; //客户端默认地址
	private static final int DEFAULT_PORT=9999;//客户端监听端口 用于接收消息
	
	private static final String USERNAME="客户端2";
	private static final String PASSWD="123456";//验证功能暂时没实现

	public static void main(String[] args){
		Message message=new Message();
		List<String> msgTos=new ArrayList<>(16);
		
		//第1步 发送登录消息(监听地址+端口+用户信息)到服务器
		message.setFlag("0"); //0-签到 1-签退 2-群发消息 3-点对点消息
		message.setMsgFrom(DEFAULT_IP+":"+DEFAULT_PORT);
		msgTos.add(DEFAULT_SERVER_IP+":"+DEFAULT_SERVER_PORT);
		message.setMsgTo(msgTos);
		message.setContent(USERNAME+""+PASSWD);
		if(!ConnectionUtil.client_sendMsg(DEFAULT_SERVER_IP, DEFAULT_SERVER_PORT, message)){
			logger.error("登录服务器失败");
			return;
		}
		logger.info("登录成功");
				
		//第2步 启动监听进程
		Client_recv client_recv=new Client_recv(DEFAULT_PORT);
		Thread recv=new Thread(client_recv);
		recv.start();
		
		try {//sleep等待启动监听进程
			Thread.sleep(2000);
		} catch (InterruptedException e) {}
		
		//第三步 循环读取用户输入并发送到其他用户
		System.out.println("请输入要发送的对象以及消息信息,格式：ip:port,ip2:port2...|消息信息 (点击回车发送)");
		Scanner scanner=new Scanner(System.in);
		while(scanner.hasNextLine()){
			String content=scanner.nextLine();
			//拆解用户消息
			int index=content.indexOf("|");
			String addrs=content.substring(0, index);
			String msg=content.substring(index+1);
			
			message.setFlag("2");
			msgTos.clear();
			msgTos.add(addrs.trim());
			message.setMsgTo(msgTos);
			message.setContent(msg);
			if(!ConnectionUtil.client_sendMsg(DEFAULT_SERVER_IP, DEFAULT_SERVER_PORT, message)){
				logger.error("发送消息给{}失败",addrs);
				return;
			}
		}		
		
	}

}
