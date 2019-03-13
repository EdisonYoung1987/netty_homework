package gupao.designPatterns.d_Proxy.i_staticProxy;

import gupao.designPatterns.d_Proxy.service.I_Person;

/**Son的静态代理类*/
public class SonProxyStatic {
	private I_Person person;
	
	public SonProxyStatic(I_Person person) {
		this.person=person;
	}
	
	public void findLove() {
		before();
		person.findLover();
		after();
	}

	private void before() {
		System.out.println("代理人(亲朋好友):  物色合适对象并安排两人见面");
	}

	private void after() {
		System.out.println("代理人(亲朋好友):  开心的收到了新人红包");		
	}
}
