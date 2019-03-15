package gupao.designPatterns.g_Template;

import java.math.BigDecimal;

/**一个计算利息的模板方法类<p>
 * 其中的方法可以是abstract等待子类实现，也可以模板类自己实现，子类也可以覆盖<br>
 *甚至模板类可以在方法中抛异常，强制子类去实现*/
public abstract class CaculateInterestTemplate {
	public void handle(String account) {
		if(isValid(account)) {//账户有效
			BigDecimal interest=caculate(account);
			display(interest);
		}else {
			System.err.println("账户无效");
		}
	}

	public boolean isValid(String account) {
		if(account==null || "".equals(account.trim())) {
			return false;
		}
		return true;
	}
	
	/**等待子类去实现*/
	public abstract BigDecimal caculate(String account) ; 
	
	public void display(BigDecimal interest) {
		System.out.println("调用模板类的打印方法， 利息是："+interest);
	}
}
