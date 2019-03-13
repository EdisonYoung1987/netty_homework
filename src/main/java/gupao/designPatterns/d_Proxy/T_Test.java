package gupao.designPatterns.d_Proxy;

import java.lang.reflect.Proxy;

import gupao.designPatterns.d_Proxy.i_staticProxy.SonProxyStatic;
import gupao.designPatterns.d_Proxy.ii_jdkDynamicProxy.SonJdkProxyHandler;
import gupao.designPatterns.d_Proxy.service.I_Person;
import gupao.designPatterns.d_Proxy.service.Son;

public class T_Test {
	public static void main(String[] args)
	{
		I_Person son=new Son();
		
		//静态代理
		SonProxyStatic proxy_Static=new SonProxyStatic(son);
		proxy_Static.findLove();
		
		//JDK动态代理
		I_Person proxy_JDK=(I_Person)new SonJdkProxyHandler().getInstance(son);
		proxy_JDK.findLover();
		proxy_JDK.hardWorking();
		
	}
}
