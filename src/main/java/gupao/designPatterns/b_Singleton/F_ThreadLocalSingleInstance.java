package gupao.designPatterns.b_Singleton;

/**通过ThreadLocal实现单线程的单例，即该线程无论执行多少次getSingleInstance()获取的都是同一个对象*/
public class F_ThreadLocalSingleInstance {
	public static final ThreadLocal<F_ThreadLocalSingleInstance> threadLocal
				=new ThreadLocal<F_ThreadLocalSingleInstance>() {
					@Override
					protected F_ThreadLocalSingleInstance initialValue() {
						return new F_ThreadLocalSingleInstance();
					}
				};
	
	//构造方法私有化
	private F_ThreadLocalSingleInstance() {}
	
	public static F_ThreadLocalSingleInstance getSingleInstance() {
		return threadLocal.get();
	}
}
