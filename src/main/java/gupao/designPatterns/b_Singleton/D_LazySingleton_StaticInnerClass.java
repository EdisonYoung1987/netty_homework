package gupao.designPatterns.b_Singleton;

import java.io.Serializable;

public class D_LazySingleton_StaticInnerClass{
	private D_LazySingleton_StaticInnerClass() {
		if(InnerClass.b_LazySingleton!=null) {//这里可以使反射方法创建实例时抛异常,避免反射破坏单例
			throw new RuntimeException("不允许创建两次对象");
		}
	}
	
	public static final D_LazySingleton_StaticInnerClass getSingleInstance() {
		return InnerClass.b_LazySingleton;
	}
	
	//静态内部类在主类被初始化时是不会被同时初始化的
	private static class InnerClass{
		static {
			System.out.println("InnerClass is init");
		}
		private static D_LazySingleton_StaticInnerClass b_LazySingleton
											=new D_LazySingleton_StaticInnerClass();
	}
	
	public static void doNothing() {}
}
