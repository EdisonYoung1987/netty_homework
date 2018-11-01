package gupao.netty.homeWork.lesson1.client;

import gupao.netty.homeWork.lesson1.base.Message;
import gupao.netty.homeWork.lesson1.util.ConnectionUtil;
import gupao.netty.homeWork.lesson1.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**客户端用于接收消息的线程<p>
 * 1.发送签到消息到SERVER
 * 2.循环接收Server转发的消息*/
public class Client_recv implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger( Client_recv.class );

	private int port;
	private static ServerSocket serverSocket=null;
	
	/**@param port 客户端监听端口*/
	public Client_recv(int port){
		this.port=port;
	}
	
	@Override
	public void run() {
		//启动监听
		if(serverSocket!=null){
			return;
		}
		
		try {
			serverSocket=new ServerSocket(port);
			logger.info("已监听端口：{}",port);
			
			while (true) {//Server接收到消息后由ServerHandler进行业务处理
				Socket socket=serverSocket.accept(); //接收连接
				BufferedReader in=null;
				PrintWriter out=null;
				
				try {
					in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String expression=in.readLine();
					Message message=Util.parseMessage(expression);
					if(message!=null){
						logger.info("接收到其他客户端{}发来的消息：{}",message.getMsgFrom(),message.getContent());
					}
				}catch(Exception e){
					e.printStackTrace();
					logger.error(e.getLocalizedMessage());
				}finally{//释放资源
					ConnectionUtil.releaseConnection(in, out, socket);
				}
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


}
