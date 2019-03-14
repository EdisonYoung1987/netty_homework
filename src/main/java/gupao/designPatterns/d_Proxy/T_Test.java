package gupao.designPatterns.d_Proxy;

import java.lang.reflect.Proxy;

import gupao.designPatterns.d_Proxy.i_staticProxy.SonProxyStatic;
import gupao.designPatterns.d_Proxy.ii_jdkDynamicProxy.SonJdkProxyHandler;
import gupao.designPatterns.d_Proxy.iii_cglibDynamicProxy.SonCGLibProxy;
import gupao.designPatterns.d_Proxy.service.I_Person;
import gupao.designPatterns.d_Proxy.service.Son;
import net.sf.cglib.core.DebuggingClassWriter;

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
		
		//CGLib动态代理
		//利用 cglib 的代理类可以将内存中的 class 文件写入本地磁盘
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"D://tmp//cglib_proxy_class/");
		I_Person proxy_CGLib=(I_Person)new SonCGLibProxy().getInstance(Son.class);
		proxy_CGLib.findLover();
		proxy_CGLib.hardWorking();
	}
}
