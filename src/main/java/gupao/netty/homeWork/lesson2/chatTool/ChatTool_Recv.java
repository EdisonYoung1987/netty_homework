package gupao.netty.homeWork.lesson2.chatTool;

import gupao.netty.homeWork.lesson2.base.Message;
import gupao.netty.homeWork.lesson2.util.MsgUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**客户端用于接收消息的线程<p>
 * 		循环接收其他用户发送的消息*/
public class ChatTool_Recv implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger( ChatTool_Recv.class );
	private CountDownLatch countDownLatch =new CountDownLatch(1);

	private static final HashMap<String, String> userMap=new HashMap<String, String>();//用于保存用户地址:端口-昵称信息
	private int port;
	
	/**@param port 客户端监听端口*/
	public ChatTool_Recv(int port){
		this.port=port;
	}
	
	@Override
	public void run() {
        ServerSocketChannel ssc =null;
        Selector selector = null;
        
        try {    
        	//打开选择器
        	selector=Selector.open();
        	
        	//打开服务器通道，并设置非阻塞
        	ssc=ServerSocketChannel.open();//服务器通道，也是open方式
        	ssc.bind(new InetSocketAddress(port), 100); //最大同时接入？
        	ssc.configureBlocking(false);     //设置非阻塞
        	
        	/*服务器通道只能对tcp连接事件感兴趣*/
            ssc.register(selector, SelectionKey.OP_ACCEPT);
		}catch(IOException e){
			e.printStackTrace();
			logger.error("客户端启动监听异常:{}",e.getLocalizedMessage());
			return;
		}
        countDownLatch.countDown();//通知主线程继续往后面执行
        
        //后续开始接收
        try {
	        while(!Thread.currentThread().isInterrupted()){//主线程通过thread.interrupt()通知停止后续处理工作
	            
				if(selector.select() == 0){//这个方法是阻塞的
				     continue;
				 }
	
	             Set<SelectionKey> keySet = selector.selectedKeys();
	             Iterator<SelectionKey> it = keySet.iterator();
	             SelectionKey key = null;
	              
	             while(it.hasNext()){
	                 key = it.next();
	                 it.remove();/*如果不删除，下次还会出现在selectionKey集合中,且删除必须用这个遍历器*/
	                
	                 if (key.isAcceptable()) {//若“接收就绪”，获取客户端连接  
	                	 SocketChannel sc=null;
	                	 try{
		                	 sc = ssc.accept();
		                     sc.configureBlocking(false);
		 					 sc.register(selector,SelectionKey.OP_READ,ByteBuffer.allocate(1024));//这里只关心读事件，并且注册一个ByteBuffer
	                	 }catch(Exception e){//不管是io异常还是通道异常
	                		 e.printStackTrace();
	                		 logger.error("处理连接异常:{}",e.getLocalizedMessage());
	                		 if(sc!=null){
	                			 try {
									sc.close();//服务器主动关闭这个异常连接请求
								} catch (IOException e1) {
									e1.printStackTrace();
								}
	                		 }
	                		 continue;
	                	 }
	 				 }else if(key.isReadable()){//可读 这个线程只关心可读
	 					/*通过SelectionKey获取通道对应的缓冲区--这个确实骚，把不同次的读取的内容多可以搞在一起*/
	//                     ByteBuffer  buffer = (ByteBuffer)key.attachment();
	 					ByteBuffer  buffer =ByteBuffer.allocate(1024);
	                     
	                     /*通过SelectionKey获取对应的通道*/
	                     SocketChannel sc = (SocketChannel) key.channel();
	                      
	                     //TODO 从底层socket读缓冲区中读入数据,实际情况如何处理粘包和半包？？
	                     if(sc.read(buffer)>0){ //-1读到末尾 0-buffer满了? >0实际读到的数量 因为客户端限制了一次性发送消息大小
	                    	 buffer.flip();//写转读 limit=position,position=0
	                    	 dealWithMessage(buffer);
	                     }
	 				 }
	             }
	        }
	        logger.error("监听线程被interrupted");
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	//释放资源
            try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
            System.out.println("server close");
        }
        
	}
	
	/**处理消息*/
	private static void dealWithMessage(ByteBuffer buffer){
		Message message=MsgUtil.parseMessage(buffer);
		if(message==null){//消息格式有问题
			return;
		}
		
		String type=message.getFlag();//消息类型
		//消息来源：地址:端口=用户昵称
		String[] user=message.getMsgFrom().split("=");

		switch (type) {
			case "0": //签到
				userMap.put(user[0], user[1]);
				break;
			case "1": //签退
				userMap.remove(user[0]);
				break;
			case "2": //点对点
				logger.info("这是一条{}发给你的消息",user[1]);
				break;
			case "3": //组内发送 qq群那种
				String groupid=message.getGroupid();
				logger.info("群id={}收到一条{}发送的消息,参与对象有{}",groupid,user[1],message.getMsgTo());
				break;
			case "4": //群发
				logger.info("这是一条{}群发的消息",user[1]);
				break;
			default:
				break;
		}
	}

	public static HashMap<String, String> getUserMap(){
		return userMap;
	}
}
