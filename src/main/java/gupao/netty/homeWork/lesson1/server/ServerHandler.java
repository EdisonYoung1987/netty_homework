package gupao.netty.homeWork.lesson1.server;

import gupao.netty.homeWork.lesson1.base.Message;
import gupao.netty.homeWork.lesson1.util.ConnectionUtil;
import gupao.netty.homeWork.lesson1.util.Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**服务器收到消息时的实际处理类.<p>
 * 一个handler处理一个socket连接*/
public class ServerHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger( ServerHandler.class );
	private Socket socket=null;
	private static ConcurrentHashMap<String, String> connetionPool;
	
	public ServerHandler(Socket socket,ConcurrentHashMap<String, String> connectionPool){
		this.socket=socket;
		connetionPool=connectionPool;
	}
	public void run() {
		BufferedReader in=null;
		PrintWriter out=null;
		
		try {
			in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(socket.getOutputStream(),true);
			String expression;  //服务端收到的消息
			Message message;		//解析消息后得到的消息对象
			expression=in.readLine();
			
			logger.info("服务端收到消息expression=[{}]",expression);
			message=Util.parseMessage(expression);
			if(message==null){//消息解析失败
				logger.error("消息解析失败:{}",message);
				ConnectionUtil.releaseConnection(in, out, socket);
				return;
			}
			
			//处理消息 
			String flag=message.getFlag();//消息类型
			String msgFrom=message.getMsgFrom();//消息来源
			
			if("0".equals(flag)){				//客户端签到登入
				connetionPool.put(msgFrom, msgFrom);
			}else if("1".equals(flag)){			//客户端签退登出
				connetionPool.remove(msgFrom);
			}else if("2".equals(flag)){			//群发消息
				for(String addrTo : message.getMsgTo()){
					ConnectionUtil.sendMsg(flag,msgFrom,addrTo, expression);
				}
			}else if("3".equals(flag)){			//点对点发送消息
				ConnectionUtil.sendMsg(flag,msgFrom,message.getMsgTo().get(0), expression);
			}else if("4".equals(flag)){			//客户端请求获取用户列表
				logger.warn("暂不支持该功能");
			}else{
				logger.warn("未知消息");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
		}finally{
			ConnectionUtil.releaseConnection(in, out, socket);
		}
	}
	
}
