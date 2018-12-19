package com.youyu.cardequity.payment.biz.component.status.paytraderefund;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 退款状态
 */
public abstract class PayTradeRefundStatus {

    /**
     * 未退款
     *
     * @return
     */
    public PayTradeRefundStatus nonRefund() {
        throw new RuntimeException("退款状态[" + this + "]不可以转化为未退款状态操作!");
    }

    /**
     * 退款中
     *
     * @return
     */
    public PayTradeRefundStatus refunding() {
        throw new RuntimeException("退款状态[" + this + "]不可以转化为退款中状态操作!");
    }

    /**
     * 退款成功
     *
     * @return
     */
    public PayTradeRefundStatus refundSucc() {
        throw new RuntimeException("退款状态[" + this + "]不可以转化为退款成功状态操作!");
    }

    /**
     * 退款失败
     *
     * @return
     */
    public PayTradeRefundStatus refundFail() {
        throw new RuntimeException("退款状态[" + this + "]不可以转化为退款失败状态操作!");
    }

    public Boolean refundFlag() {
        return false;
    }

    public boolean isRefundSucc() {
        return false;
    }
}
