package gupao.netty.homeWork.lesson1.util;

import gupao.netty.homeWork.lesson1.base.Message;
import gupao.netty.homeWork.lesson1.client.Client_recv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionUtil {
	private static final Logger logger = LoggerFactory.getLogger( ConnectionUtil.class );

	/**根据传入的ip:port,建立连接并发送消息，然后释放资源-短连接
	 * @param addrFrom- 消息来源者 ip:port
	 * @param msgTo  - 消息接收者 ip:port
	 * @param content 消息*/
	public static boolean sendMsg(String msgType,String msgFrom,String msgTo,String content){
		String[] addr=msgTo.split(":");
		String host=addr[0];
		String port=addr[1];
		PrintWriter out=null;
		Socket socket=null;
		BufferedReader in=null;//这个用不上 纯粹是懒得再封装一个释放资源方法
		logger.info("[{}] [{}] [{}] [{}]",msgType,msgFrom,msgTo,content);
		try {
			socket=new Socket(host, Integer.parseInt(port));
			out=new PrintWriter(socket.getOutputStream());
			//其中的write()方法，本身不会写入换行符，如果用write()写入了信息，在另一端如果用readLine()方法。
			//由于读不到换行符，意味中读不到结束标记，然后由于IO流是阻塞式的，所以程序就是一直卡在那里不动了。
			//原因即为缺少回车标识。如果在写入的时候加上“\r\n”,就可以解决这个问题了。而println()就自动加上了换行符了
			out.println(content);
		} catch (Exception e) { //无论什么异常都暂时不处理 TODO
			e.printStackTrace();
			return false;
		}finally{
			releaseConnection(in, out, socket);
		}
		return true;
	}
	/**用于SERVER端将加工后的message发送出去-短连接*/
	public static boolean server_sendMsg(Message message){
		boolean result=true;
		List<String> msgTos=message.getMsgTo();
		for(String msgTo : msgTos){
			boolean res=sendMsg(message.getFlag(), message.getMsgFrom(), msgTo, message.getContent());
			if(res==false){
				result=false;
				logger.error("发送消息[{}]给[{}]失败！",message.getContent(),msgTo);
			}
		}
		return result;
	}
	
	/**用于客户端将加工后的message发送给服务端-短连接*/
	public static boolean client_sendMsg(String ip,int port,Message message){
		String content=Util.packMessage(message);
		if(content==null){
			logger.error("打包message失败:message={}",message);
			return false;
		}
		
		if(sendMsg(message.getFlag(), message.getMsgFrom(), ip+":"+port,content )==false){
			logger.error("发送消息[{}]给服务端失败！",message);
			return false;
		}
		return true;
	}
	
	/**释放资源*/
	public static void releaseConnection(BufferedReader in,PrintWriter out,Socket socket){
		if(in!=null){
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			in=null;
		}
		if(out!=null){
			out.close();
			out=null;
		}
		if(socket!=null){
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			socket=null;
		}
	}
}
