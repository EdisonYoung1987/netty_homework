package gupao.designPatterns.d_Proxy;

import gupao.designPatterns.d_Proxy.i_staticProxy.SonProxyStatic;
import gupao.designPatterns.d_Proxy.service.I_Person;
import gupao.designPatterns.d_Proxy.service.Son;

public class T_Test {
	public static void main(String[] args)
	{
		I_Person son=new Son();
		
		//静态代理
		SonProxyStatic proxy_Static=new SonProxyStatic(son);
		proxy_Static.findLove();
		
	}
}
