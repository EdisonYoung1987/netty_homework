package gupao.designPatterns.b_Singleton;

import java.io.Serializable;

/**和A_HungrySingleInstance2没有区别，只是增加了可序列化，重写了readResolve()方法，为后面的
 * 序列化破坏单例提供对象*/
public class A_HungrySingleInstance2 implements Serializable {
	private static A_HungrySingleInstance2 A_HungrySinggleInstance=new A_HungrySingleInstance2();
	
	//构造方法私有化
	private A_HungrySingleInstance2() {	}
	
	public static A_HungrySingleInstance2 getSingleInstance() {
		return A_HungrySinggleInstance;
	}

	private Object readResolve(){
		return A_HungrySinggleInstance;
	}
	
}
