package gupao.netty.homeWork.lesson1.util;

import gupao.netty.homeWork.lesson1.base.Message;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**工具类*/
public class Util {
	private static final Logger logger = LoggerFactory.getLogger( Util.class );

	/**服务端将客户端传递的消息解析成Message对象.<p>
	 * @param expression 标志位|来源ip:port|发送至ip:port,ip:port,ip:port|消息内容  组成的字串
	 * @return 失败返回null  */
	public static Message parseMessage(String expression){
		if(!msgChk(expression)){//数据有效性校验失败
			return null;
		}
		
		Message message=new Message();
		String[] list=expression.split("\\|");
		
		//分别解析消息内容 暂时不校验 TODO
		message.setFlag(list[0]);//标志位
		message.setMsgFrom(list[1]); //来源ip:port
		message.setMsgTo( Arrays.asList(list[2].split(",")));//对方ip:port组成的列表
		List<String> msgTos=message.getMsgTo();
		
		message.setContent(list[3]); //内容
		return message;
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
		StringBuilder sb=new StringBuilder();
		
		//先获取消息接收对象
		sb.append(message.getFlag());
		sb.append("|");
		sb.append(message.getMsgFrom());
		sb.append("|");
		List<String> msgTos=message.getMsgTo();
		for(String msgTo:msgTos){//群发地址用,隔开
			sb.append(msgTo+",");
		}
		sb.append("|");
		sb.append(message.getContent());
		logger.debug("打包后的message={}",sb.toString());
		if(msgChk(sb.toString())){
			return sb.toString();
		}else{
			return null;
		}
	}
	
	/**检查message的格式是否正确<p>
	 * 格式：标志位|来源ip:port|发送至ip:port,ip:port,ip:port|消息内容*/
	public static boolean msgChk(String expression){
		if(expression==null||"".equals(expression.trim())){
			logger.error("expression is null");
			return false;
		}
		
		String[] list=expression.split("\\|");//TODO 暂时先用|作为分隔，有时间再优化
		if(list.length!=4){
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
}
