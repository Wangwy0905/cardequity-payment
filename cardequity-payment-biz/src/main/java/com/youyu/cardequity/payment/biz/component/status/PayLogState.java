package com.youyu.cardequity.payment.biz.component.status;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 支付状态
 */
public abstract class PayLogState {

    /**
     * 未支付
     *
     * @return
     */
    public PayLogState nonPayment() {
        throw new RuntimeException("支付状态[" + this + "]不可以转化为未支付状态操作!");
    }

    /**
     * 支付排队中
     *
     * @return
     */
    public PayLogState paymentQueuing() {
        throw new RuntimeException("支付状态[" + this + "]不可以转化为支付排队中状态操作!");
    }

    /**
     * 支付中
     *
     * @return
     */
    public PayLogState paymenting() {
        throw new RuntimeException("支付状态[" + this + "]不可以转化为支付中状态操作!");
    }

    /**
     * 支付成功
     *
     * @return
     */
    public PayLogState paymentSucc() {
        throw new RuntimeException("支付状态[" + this + "]不可以转化为支付成功状态操作!");
    }

    /**
     * 支付失败
     *
     * @return
     */
    public PayLogState paymentFail() {
        throw new RuntimeException("支付状态[" + this + "]不可以转化为支付失败状态操作!");
    }

    /**
     * 是否可以进行交易查询
     *
     * @return
     */
    public boolean canPayTradeQuery() {
        return true;
    }
}
