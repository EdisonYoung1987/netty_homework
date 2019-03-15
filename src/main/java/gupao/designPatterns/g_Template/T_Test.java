package gupao.designPatterns.g_Template;

public class T_Test {

	public static void main(String[] args) {
		CaculateInterestTemplate template1=new CaculateInterest1();
		CaculateInterestTemplate template2=new CaculateInterest2();

		template1.handle("张三");
		template2.handle("张三");
	}

}
