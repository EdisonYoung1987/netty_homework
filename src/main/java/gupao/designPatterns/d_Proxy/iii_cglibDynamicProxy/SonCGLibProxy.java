package gupao.designPatterns.d_Proxy.iii_cglibDynamicProxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class SonCGLibProxy implements MethodInterceptor{

	public Object getInstance(Class<?> clazz) {//Enhancer类似Proxy，都是生成代理类
		Enhancer enhancer=new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		
		return enhancer.create();
	}
	
	@Override
	public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		before();
//		Object res=method.invoke(target, args); //这个会抛异常。。。。
		Object res=proxy.invokeSuper(target, args);
		after();
		return res;
	}

	private void before() {
		System.out.println("CGLib动态代理前置方法执行");
	}

	private void after() {
		System.out.println("CGLib动态代理后置方法执行");		
	}
}
