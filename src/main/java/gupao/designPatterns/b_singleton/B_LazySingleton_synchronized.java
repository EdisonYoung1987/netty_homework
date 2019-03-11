package gupao.designPatterns.b_singleton;

public class B_LazySingleton_synchronized {
	private static B_LazySingleton_synchronized b_LazySingleton=null;
	
	private B_LazySingleton_synchronized() {}
	
	public static synchronized B_LazySingleton_synchronized getSingleInstance() {
		if(b_LazySingleton==null) {
			b_LazySingleton=new B_LazySingleton_synchronized();
		}
		return b_LazySingleton;
	}
}
