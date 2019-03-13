package gupao.designPatterns.d_Proxy.ii_jdkDynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SonJdkProxyHandler implements InvocationHandler{
	private Object target;
	
	//被代理对象的获取
	public Object getInstance(Object target) {
		this.target=target;//传入被代理对象是因为invoke方法会用到
		Class<?> clazz = target.getClass();
		return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		before();
		Object res=method.invoke(this.target, args);//这里需要传入被代理对象
		after();
		return res;
	}
	
	private void before() {
		System.out.println("jdk动态代理前置方法执行");
	}

	private void after() {
		System.out.println("jdk动态代理后置方法执行");		
	}

}
