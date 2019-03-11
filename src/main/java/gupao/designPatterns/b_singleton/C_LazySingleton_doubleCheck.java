package gupao.designPatterns.b_singleton;

public class C_LazySingleton_doubleCheck {
	private static volatile C_LazySingleton_doubleCheck b_LazySingleton=null;
	private static final byte[] lockObj=new byte[1];
	
	private C_LazySingleton_doubleCheck() {}
	
	public static  C_LazySingleton_doubleCheck getSingleInstance() {
		if(b_LazySingleton==null) {
			synchronized (lockObj) {
				if(b_LazySingleton==null)
					b_LazySingleton=new C_LazySingleton_doubleCheck();
			}
		}
		return b_LazySingleton;
	}
}
