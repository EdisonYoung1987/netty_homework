package gupao.designPatterns.f_Strategy;

import java.util.HashMap;
import java.util.Map;

public class PromotionStrategyFactory {
	private static Map<Strategies,IPromotionStrategy> PROMOTION_STRATEGY_MAP=new HashMap<>();
	static {
		PROMOTION_STRATEGY_MAP.put(Strategies.COUPON,new CouponStrategy());
        PROMOTION_STRATEGY_MAP.put(Strategies.CASHBACK,new CashbackStrategy());
        PROMOTION_STRATEGY_MAP.put(Strategies.GROUPBUY,new GroupbuyStrategy());
	}
    private static final IPromotionStrategy NON_PROMOTION = new EmptyStrategy();

	
	private PromotionStrategyFactory(){}
	
	public static IPromotionStrategy getPromotionStrategy(Strategies strategies){
        IPromotionStrategy promotionStrategy = PROMOTION_STRATEGY_MAP.get(strategies);
        return promotionStrategy == null ? NON_PROMOTION : promotionStrategy;
    }
	
	public enum Strategies{
		COUPON("优惠券",0),
		CASHBACK("返现",1),
		GROUPBUY("团购",2),
		EMPTY("无优惠",3);
		private Strategies(String name,int idx) {}
	}
}
