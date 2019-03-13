package gupao.designPatterns.b_Singleton;

public class T_Test {
	public static void main(String[] args) {
		A_HungrySingleInstance.doNothing();
		D_LazySingleton_StaticInnerClass.doNothing();
		System.out.println("------------");
		for(int i=0;i<10;i++) {
			new Thread(new MyRunnable()).start();
		}
		
		//测试ThreadLocal方式的线程单例
		System.out.println(F_ThreadLocalSingleInstance.getSingleInstance());
		System.out.println(F_ThreadLocalSingleInstance.getSingleInstance());
		System.out.println(F_ThreadLocalSingleInstance.getSingleInstance());
		System.out.println(F_ThreadLocalSingleInstance.getSingleInstance());

	}
}
class MyRunnable implements Runnable{
	
	@Override
	public void run() {
		//饿汉式：加载类时已经初始化完成，线程安全，可能浪费内存
//		System.out.println(Thread.currentThread().getName()+" "+A_HungrySingleInstance.getSingleInstance());
	
		//懒汉式：synchronized方法，并发效率低
//		System.out.println(Thread.currentThread().getName()+" "+B_LazySingleton_synchronized.getSingleInstance());
			
		//懒汉式：synchronized+doubleCheck，优化
//		System.out.println(Thread.currentThread().getName()+" "+C_LazySingleton_doubleCheck.getSingleInstance());

		//静态内部类：最优化
		System.out.println(Thread.currentThread().getName()+" "+D_LazySingleton_StaticInnerClass.getSingleInstance());

	}
	
}
