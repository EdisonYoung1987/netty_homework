package gupao.netty.homeWork.lesson1;

import static org.junit.Assert.assertTrue;
import gupao.netty.homeWork.lesson1.base.Message;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public  void parseMessage(){
		/**测试说明：
		 * 1.运行server/Server.java启动服务端
		 * 		已监听端口：7777
		 * 2.运行client/Client.java启动客户端1
		 * 		客户端转发消息开始：
		 * 		登录成功
		 * 		已监听端口：8888
		 * 		输出：请输入要发送的对象以及消息信息,格式：ip:port,ip2:port2...|消息信息 (点击回车发送)
		 * 3.运行client/Client2.java启动客户端2
		 * 		客户端转发消息开始：
		 * 		登录成功
		 * 		已监听端口：9999
		 * 		输出：请输入要发送的对象以及消息信息,格式：ip:port,ip2:port2...|消息信息 (点击回车发送)
		 * 4.客户端2的console输入：127.0.0.1:8888|你好啊1   然后回车
		 * 	 客户单1的console显示消息：
		 * 			接收到其他客户端127.0.0.1:9999发来的消息：你好啊1*/
	}
}
