package gupao.netty.homeWork.lesson2.chatTool;

import gupao.netty.homeWork.lesson2.base.Buffers;
import gupao.netty.homeWork.lesson2.base.Message;
import gupao.netty.homeWork.lesson2.base.UserInfo;
import gupao.netty.homeWork.lesson2.util.MsgUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**客户端用于接收消息的线程<p>
 * 		循环接收其他用户发送的消息*/
public class ChatTool_Recv implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger( ChatTool_Recv.class );
	private CountDownLatch countDownLatch =new CountDownLatch(1);

	private int port;
	private List<UserInfo> userlist;//在线好友列表
	
	/**@param port 客户端监听端口*/
	public ChatTool_Recv(int port,List<UserInfo> userlist){
		this.port=port;
		this.userlist=userlist;
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
        	ssc.configureBlocking(false);     //设置非阻塞,为了兼容bio，默认是阻塞模式的
        	
        	/*服务器通道只能对tcp连接事件感兴趣*/
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            countDownLatch.countDown();//通知主线程继续往后面执行
        }catch(IOException e){
			e.printStackTrace();
			logger.error("客户端启动监听异常:{}",e.getLocalizedMessage());
			return;
		}
   
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
		                     //其实这里注册读还是写还是都注册需要看处理顺序，假设是先写欢迎语句，再读请求，再写响应
		                     //最好在这里就只注册写关注，因为写操作是异步的，不一定一次就能写完，写不完就通过attachment下次继续写
		                     //，直到写完，再注册读关注并取消写关注，一次读不完就多读几次，直到读完，再重复写。
		 					 sc.register(selector,SelectionKey.OP_READ|SelectionKey.OP_WRITE,new Buffers(1024, 1024));//关心读写事件，并且注册一个ByteBuffer
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
	 				 }else if(key.isReadable()){//可读 
	 					 /*通过SelectionKey获取通道对应的缓冲区--这个确实骚，把不同次的读取的内容多可以搞在一起*/
	 					 Buffers  buffers = (Buffers)key.attachment();
	 					 ByteBuffer readBuffer=buffers.getReadBuffer(); //获取读buffer
	 					 ByteBuffer writeBuffer=buffers.getWriteBuffer(); //获取读buffer
	 					 
	                     /*通过SelectionKey获取对应的通道*/
	                     SocketChannel sc = (SocketChannel) key.channel();
	                      
	                     //TODO 从底层socket读缓冲区中读入数据,实际情况如何处理粘包和半包？？
	                     if(sc.read(readBuffer)>0){ //-1读到末尾 0-buffer满了? >0实际读到的数量 因为客户端限制了一次性发送消息大小
	                    	 readBuffer.flip();//写转读 limit=position,position=0
	                    	 String response=dealWithMessage(readBuffer); 
	                    	 readBuffer.clear(); //为下次读做准备
	                    	 
	                    	 //这里应该回复响应报文，这样其他客户端可以把本机信息添加到列表中
	                    	 if(response==null){
	                    		 writeBuffer.clear();//表示没有内容发送
	                    	 }else{
	                    		 writeBuffer.put(response.getBytes());
	                     
	                    	 }
	                     }
	 				 }else if(key.isWritable()){//可写
	 					 /**注意通常不会在没有写东西的情况下就注册写就绪事件，因为在发送缓冲区未满的
	 					  * 情况下始终是可写的，而且注册写事件，而又不用写数据，则缓冲区未满总会响应
	 					  * 写事件就绪，很容易造成CPU空转，出现消耗CPU100%的情况。
	 					  * 但是，因为非阻塞，如果高并发，有可能channel在write时，没能一次性写完，就需要
	 					  * 注册写就绪，把剩下的都写出去*/
//	 					 System.out.println("可写了，把信息发送出去");
	 					 Buffers  buffers = (Buffers)key.attachment();
	 					 ByteBuffer writeBuffer=buffers.getWriteBuffer(); //获取读buffer
	 					 
	                     /*通过SelectionKey获取对应的通道*/
	                     SocketChannel sc = (SocketChannel) key.channel();
	                     if(writeBuffer.limit()==0){//不需要写
//	                    	 System.out.println("不需要写");
	                    	 continue;
	                     }
	                     //从底层socket读缓冲区中读入数据,实际情况如何处理粘包和半包？？
	                     writeBuffer.flip();
                         /*将程序定义的缓冲区中的内容写入到socket的写缓冲区中*/
                         sc.write(writeBuffer);
                         writeBuffer.clear();
                         
                         //★★如果写完了，这里要么关闭连接，要么取消读注册
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
	
	/**处理消息
	 * @return 回复信息 null表示无须回复*/
	private String dealWithMessage(ByteBuffer buffer){
		String response="响应消息：听不懂，不知道你在说什么"; //响应报文
		Message message=MsgUtil.parseMessage(buffer);
		if(message==null){//消息格式有问题
			//TODO组装响应报文
			response="响应消息：听不懂，不知道你在说什么";
			return response;
		}
		
		String type=message.getFlag();//消息类型
		//消息来源：地址:端口=用户昵称
		UserInfo user=message.getMsgFrom();
		
		switch (type) {
			case "0": //上线
				if(!userlist.contains(user))
					userlist.add(user);
				
				//TODO组装响应报文
				response="响应消息：我收到你的消息，请把我加入你的好友列表";
				break;
			case "1": //签退
				userlist.remove(user);
				
				//这里不用组织响应报文
				break;
			case "2": //点对点
				logger.warn("这是一条{}发给你的消息",user.getName());
				
				//TODO组装响应报文
				response="响应消息：已收到消息";
				break;
			case "3": //组内发送 qq群那种
				String groupid=message.getGroupid();
				logger.warn("群id={}收到一条{}发送的消息,参与对象有{}",groupid,user.getName(),message.getMsgTo());
				
				//这里不用组织响应报文
				break;
			case "4": //群发
				logger.warn("这是一条{}群发的消息",user.getName());
				
				//这里不用组织响应报文
				break;
			default:
				//TODO组装响应报文
				response="响应消息：听不懂，不知道你在说什么";
				break;
		}
		return response;
	}

	
}
