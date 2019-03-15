package gupao.designPatterns.f_Strategy;

/**
 * 优惠活动
 * Created by Tom
 */
public class PromotionActivity {
    private IPromotionStrategy promotionStrategy;

    public PromotionActivity(IPromotionStrategy promotionStrategy) {
        this.promotionStrategy = promotionStrategy;
    }

    public void execute(){
        promotionStrategy.doPromotion();
    }

}
