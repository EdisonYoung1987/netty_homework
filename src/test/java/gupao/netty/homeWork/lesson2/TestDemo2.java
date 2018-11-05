package gupao.netty.homeWork.lesson2;

import gupao.netty.homeWork.lesson2.chatTool.ChatTool;

public class TestDemo2 {

	public static void main(String[] args) {
		ChatTool chatTool=new ChatTool("李四",8889);
		chatTool.start();
	}

}
