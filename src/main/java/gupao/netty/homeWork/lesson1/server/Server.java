package gupao.netty.homeWork.lesson1.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端实现*/
public class Server {
	private static final Logger logger = LoggerFactory.getLogger( Server.class );

	private static final int DEFAULT_PORT=7777; //默认端口号
	private static ServerSocket serverSocket; //单例serversocket
	//用户列表 ip:port - 用户名
	private static ConcurrentHashMap<String, String> connetionPool=new ConcurrentHashMap<String, String>();
	
	public static void start(){
		start(DEFAULT_PORT);
	}
	public static void start(int port){
		if(serverSocket!=null){
			return;
		}
		
		try {
			serverSocket=new ServerSocket(port);
			logger.info("已监听端口：{}",port);
			
			while (true) {//Server接收到消息后由ServerHandler进行业务处理
				Socket socket=serverSocket.accept(); //接收连接
				new Thread(new ServerHandler(socket,connetionPool)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(serverSocket!=null){
				try {
					serverSocket.close();
					serverSocket=null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[]  args){
		Server.start();
	}
}
