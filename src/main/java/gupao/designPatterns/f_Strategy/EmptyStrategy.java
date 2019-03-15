package gupao.designPatterns.f_Strategy;
/**
 * 无优惠
 * Created by Tom
 */
public class EmptyStrategy implements IPromotionStrategy {
    public void doPromotion() {
        System.out.println("无促销活动");
    }
}
