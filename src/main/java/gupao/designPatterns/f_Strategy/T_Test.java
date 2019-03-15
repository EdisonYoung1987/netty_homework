package gupao.designPatterns.f_Strategy;

import gupao.designPatterns.f_Strategy.PromotionStrategyFactory.Strategies;

public class T_Test {

	public static void main(String[] args) {
		//简单版本-写死策略(促销方式)
		PromotionActivity promotionActivity1=new PromotionActivity(new CouponStrategy());
		PromotionActivity promotionActivity2=new PromotionActivity(new CashbackStrategy());
		promotionActivity1.execute();
		promotionActivity2.execute();

		//进阶-根据传入参数或者配置选择不同的促销方式
		IPromotionStrategy strategy=null;
		String option="COUPON"; //假设客户传入的
		if("COUPON".equals(option)) {
			strategy=new CouponStrategy();
		}else if("CASHBACK".equals(option)) {
			strategy=new CashbackStrategy();
		}else if("GROUPBUY".equals(option)) {
			strategy=new GroupbuyStrategy();
		}else {
			strategy=new EmptyStrategy();
		}
		PromotionActivity promotionActivity=new PromotionActivity(strategy);
		promotionActivity.execute();
		
		//使用单例+工厂模式简化上面的if-else
		promotionActivity=new PromotionActivity(
				PromotionStrategyFactory.getPromotionStrategy(Strategies.GROUPBUY));
		promotionActivity.execute();
		
	}

}
