package gupao.designPatterns.f_Strategy;

/**
 * 返现活动
 * Created by Tom
 */
public class CashbackStrategy implements IPromotionStrategy {

    public void doPromotion() {
        System.out.println("返现促销,返回的金额转到支付宝账号");
    }
}
