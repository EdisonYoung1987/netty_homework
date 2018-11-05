package gupao.netty.homeWork.lesson2.util;

import gupao.netty.homeWork.lesson2.base.Message;
import gupao.netty.homeWork.lesson2.base.UserInfo;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**消息报文处理工具类
 * * 消息格式：
 * 		消息类型|消息来源|消息对象|消息内容
 * 		消息类型：0-上线  1-下线  2-点对点消息 3-组内消息 4-群发消息  
 * 		消息来源：地址:端口=用户昵称
 * 		消息组id: GROUPID(点对点或群发)=0 /GROUPID=实际群ID
 * 		消息对象集合: 地址1:端口1=用户昵称1,地址2:端口2=用户昵称2...地址2:端口2=用户昵称2
 * 		消息内容: 如果超过指定长度，则分批次发送
 */
public class MsgUtil {
	private static final Logger logger = LoggerFactory.getLogger( MsgUtil.class );
	private static final Charset utf8 = Charset.forName("UTF-8");

	/**服务端将客户端传递的消息解析成Message对象.<p>
	 * @param expression 具体格式看message
	 * @return 失败返回null  */
	public static Message parseMessage(String expression){
		if(!msgChk(expression)){//数据有效性校验失败
			return null;
		}
		
		Message message=new Message();
		String[] list=expression.split("\\|");
		
		//分别解析消息内容 暂时不校验 TODO
		message.setFlag(list[0]);//标志位
		String msgfrom=list[1];
		message.setMsgFrom(getUser(msgfrom));
		
		message.setGroupid(list[2]);
		
		List<UserInfo> userlist=new ArrayList<UserInfo>();
		String[] list2=list[3].split(",");
		for(String str:list2){
			userlist.add(getUser(str));
		}
		message.setMsgTo(userlist);
		
		message.setContent(list[4]); //内容
		return message;
	}
	
	
	public static Message parseMessage(ByteBuffer buffer){
		 /*解码显示，客户端发送来的信息*/
        CharBuffer cb = utf8.decode(buffer);
        System.err.println("收到消息："+new String(cb.array()));
        return parseMessage(new String(cb.array()));
	}
	
	/**组装消息，用于客户端
	 * @param msgType 消息类型  0-签退 1-签到 2-群发消息 3-点对点消息
	 * @return 失败返回null  
	 * */
	public static String packMessage(String msgType,String msgFrom,String msgTo,String content){
		StringBuilder sb=new StringBuilder();
		
		//先获取消息接收对象
		sb.append(msgType);
		sb.append("|");
		sb.append(msgFrom);
		sb.append("|");
		sb.append(msgTo);  //群发地址用,隔开
		sb.append("|");
		sb.append(content);
		
		if(msgChk(sb.toString())){
			return sb.toString();
		}else{
			return null;
		}
	}
	/**失败返回null*/
	public static String packMessage(Message message){
		return message.toString();
	}
	
	/**检查message的格式是否正确<p>
	 * 格式：标志位|来源ip:port|发送至ip:port,ip:port,ip:port|消息内容*/
	public static boolean msgChk(String expression){
		if(expression==null||"".equals(expression.trim())){
			logger.error("expression is null");
			return false;
		}
		
		String[] list=expression.split("\\|");//TODO 暂时先用|作为分隔，有时间再优化
		if(list.length<5){
			logger.error("收到消息格式不正确：{}",expression);
			return false;
		}
		
		String flag=list[0];//消息类型
		String msgFrom=list[1];//消息来源 ip:port
		String msgTo=list[2];//消息对象 ip:port,ip:port,...ip:port
		
//		if(!flag.matches("0|1|2|3|4|5|6|7|8|9")){
//			logger.error("收到消息类型不正确：{}",expression);
//			return false;
//		}//TODO 空了改
		
		
		return true;
	}
	
	/**检查是否ip:port形式*/
	public static boolean isValidAddr(String addr){
		if(!addr.matches("[0-9]")){
			//TODO 有时间再优化
		}
		return true;
	}
	
	/**根据 地址:端口=昵称 的字符串来拆分成为userInfo*/
	public static UserInfo getUser(String exp){
		UserInfo userInfo=new UserInfo();
		
		String[] tmp=exp.split(":");
		userInfo.setIp(tmp[0]);
		
		String[] tmp2=tmp[1].split("=");
		userInfo.setPort(Integer.parseInt(tmp2[0]));
		userInfo.setName(tmp2[1]);
		
		return userInfo;
	}
}
