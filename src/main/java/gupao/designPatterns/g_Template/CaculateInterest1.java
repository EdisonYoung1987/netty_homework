package gupao.designPatterns.g_Template;

import java.math.BigDecimal;

public class CaculateInterest1 extends CaculateInterestTemplate{

	@Override
	public BigDecimal caculate(String account) {
		return new BigDecimal("100.01");
	}

	@Override
	public void display(BigDecimal interest) {
		System.out.println("子类自己的打印方法，利息是："+interest);
	}
	
	

}
