package gupao.designPatterns.b_Singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**通过反射破坏单例*/
public class O_ReflectDestroySingleInstance {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		//获取内部类的单例对象
		D_LazySingleton_StaticInnerClass singleInstance
						=D_LazySingleton_StaticInnerClass.getSingleInstance();
		
		Class<?> clazz=D_LazySingleton_StaticInnerClass.class;
		Constructor<?> cons=null;
//		cons=clazz.getConstructor(); //这个是返回public的构造方法 ，因为不存在，所以会抛异常
		cons=clazz.getDeclaredConstructor(null);//这个是返回所有类型public/private等的构造方法
		cons.setAccessible(true);
		D_LazySingleton_StaticInnerClass singleInstance2
					=(D_LazySingleton_StaticInnerClass) cons.newInstance();
		
		System.out.println(singleInstance);
		System.out.println(singleInstance2);
		System.out.println("两对象是否相同："+(singleInstance==singleInstance2));//false
	}

}
