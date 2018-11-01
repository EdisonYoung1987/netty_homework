package gupao.netty.homeWork.lesson1.base;

import java.util.List;

/**聊天系统消息类*/
public class Message {
	/**消息类型 0-签到 1-签退 2-群发消息 3-点对点消息*/
	String  flag;  
	
	/**消息来源ip:port*/
	String msgFrom;
	
	/**消息对象 List\<String ip:port\>*/
	List<String> msgTo;
	
	/**消息内容*/
	String  content;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMsgFrom() {
		return msgFrom;
	}

	public void setMsgFrom(String msgFrom) {
		this.msgFrom = msgFrom;
	}

	public List<String> getMsgTo() {
		return msgTo;
	}

	public void setMsgTo(List<String> msgTo) {
		this.msgTo = msgTo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Message [flag=" + flag + ", msgFrom=" + msgFrom + ", msgTo="
				+ msgTo + ", content=" + content + "]";
	}
	
	
}
