package gupao.netty.homeWork.lesson2.chatTool;

import gupao.netty.homeWork.lesson2.base.Message;
import gupao.netty.homeWork.lesson2.base.UserInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ChatTool_Send {
	private List<UserInfo> userList; //用户列表
	
	public ChatTool_Send(List<UserInfo> userList){
		this.userList=userList;
	}
	
	public  boolean send_single(String context,UserInfo myself){//感觉发送完全没必要使用select
		//根据用户传递的昵称获取实际ip：port
		int index=context.indexOf("|");
		if(index==-1){
			System.err.println("消息格式有问题");
			return false;
		}
		String part1=context.substring(0,index); //用户昵称/或者ip:port
		String part2=context.substring(index+1);  //实际消息   暂不考虑消息格式有问题
		
		UserInfo toUser=null;
		
		if(part1.indexOf(":")==-1){//不是实际地址:端口
			System.out.println("不是实际地址:端口");
			for(UserInfo usertmp:this.userList){
				if(part1.equals(usertmp.getName())){
					toUser=usertmp;
				}
			}
		}else{
			System.out.println("是实际地址:端口");
			toUser=new UserInfo();
			String[] addr=part1.split(":");
			toUser.setIp(addr[0]);
			toUser.setPort(Integer.parseInt(addr[1]));
		}
		
		if(toUser==null){//用户不存在
			System.err.println("用户不存在:"+part1);
			return false;
		}
		
		try{
			SocketChannel sc=SocketChannel.open();
			sc.connect(new InetSocketAddress(toUser.getIp(),toUser.getPort()));
			ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
			
			//message处理， 不该放在这里
			List<UserInfo> toList=new ArrayList<UserInfo>();
			toList.add(toUser);
			Message message=new Message("0", myself, "0", toList, context);
			
			byteBuffer.put(message.toString().getBytes("UTF-8"));
	        byteBuffer.flip();
			sc.write(byteBuffer);
			byteBuffer.clear();
			System.out.println("发送完毕，开始等待响应");
			
			sc.read(byteBuffer);
			byteBuffer.flip();
			CharBuffer cb = Charset.forName("UTF-8").decode(byteBuffer);
            /*显示接收到由服务器发送的信息*/
            System.out.println(cb.array());
            System.out.println("关闭连接");
			sc.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return true;
	}
	
	public  boolean send_group(String context,String groupid){ //组内消息发送
		System.err.println("组内消息，暂未实现");
		return true;
	}
	
	public  void send_broadCast(int onOffLine){ //广播消息 上线 下线时使用
		System.err.println("广播消息，暂未实现");
		return;
	}
}
