package gupao.designPatterns.g_Template;

import java.math.BigDecimal;

public class CaculateInterest2 extends CaculateInterestTemplate{

	@Override
	public BigDecimal caculate(String account) {
		return new BigDecimal("200.02");
	}

}
