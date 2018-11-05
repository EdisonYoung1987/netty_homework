package gupao.netty.homeWork.lesson2;

import gupao.netty.homeWork.lesson2.chatTool.ChatTool;

public class TestDemo1 {

	public static void main(String[] args) {
		ChatTool chatTool=new ChatTool("张三",8888);
		chatTool.start();
	}

}
