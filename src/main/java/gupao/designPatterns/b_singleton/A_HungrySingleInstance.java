package gupao.designPatterns.b_singleton;

import java.io.Serializable;

/**线程安全、可能浪费内存*/
public class A_HungrySingleInstance {
	private static A_HungrySingleInstance A_HungrySinggleInstance=new A_HungrySingleInstance();
	static {
		System.out.println("静态代码块被执行");
	}
	
	//构造方法私有化
	private A_HungrySingleInstance() {	}
	
	public static A_HungrySingleInstance getSingleInstance() {
		return A_HungrySinggleInstance;
	}
	public static void doNothing() {//只要这个方法被调用，静态代码块和静态变量都会被处理
		
	}
	
}
