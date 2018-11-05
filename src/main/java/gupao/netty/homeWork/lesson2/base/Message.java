package gupao.netty.homeWork.lesson2.base;

import java.util.List;

/**聊天系统消息类
 * 消息格式：
 * 		消息类型|消息来源|消息组群号|消息对象|消息内容
 * 		消息类型：0-上线  1-下线  2-点对点消息 3-组内消息 4-群发消息  
 * 		消息来源：地址:端口=用户昵称
 * 		消息组群: GROUPID(点对点或群发)=0 /GROUPID=实际群ID
		消息对象: 地址1:端口1=用户昵称1,地址2:端口2=用户昵称2...地址2:端口2=用户昵称2
 * 		消息内容: 如果超过指定长度，则分批次发送*/
public class Message {
	/**消息类型 0-签到 1-签退 2-群发消息 3-点对点消息*/
	String  flag;  
	
	/**消息来源ip:port*/
	UserInfo msgFrom;
	
	/**消息组群号*/
	String groupid;
	
	/**消息对象 List\<String ip:port\>*/
	List<UserInfo> msgTo;
	
	/**消息内容*/
	String  content;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public UserInfo getMsgFrom() {
		return msgFrom;
	}

	public void setMsgFrom(UserInfo msgFrom) {
		this.msgFrom = msgFrom;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public List<UserInfo> getMsgTo() {
		return msgTo;
	}

	public void setMsgTo(List<UserInfo> msgTo) {
		this.msgTo = msgTo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Message(){
		super();
	}
	
	public Message(String flag, UserInfo msgFrom, String groupid,
			List<UserInfo> msgTo, String content) {
		super();
		this.flag = flag;
		this.msgFrom = msgFrom;
		this.groupid = groupid;
		this.msgTo = msgTo;
		this.content = content;
	}

	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append(flag);
		sb.append("|");
		sb.append(msgFrom.toString());
		sb.append("|");
		sb.append(groupid);
		sb.append("|");
		for(int i=0;i<msgTo.size();i++){
			sb.append(msgTo.get(i).toString());
			if(i!=msgTo.size()-1){
				sb.append(",");
			}
		}
		sb.append("|");
		sb.append(content);
		
		return sb.toString();
	}
	
	
}
