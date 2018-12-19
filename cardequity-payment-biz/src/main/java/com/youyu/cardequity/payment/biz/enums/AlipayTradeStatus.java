package com.youyu.cardequity.payment.biz.enums;

import lombok.Getter;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 支付宝交易状态
 */
@Getter
public enum AlipayTradeStatus {

    TRADE_FINISHED("TRADE_FINISHED", "交易完成:触发通知"),
    TRADE_SUCCESS("TRADE_SUCCESS", "支付成功:触发通知"),
    WAIT_BUYER_PAY("WAIT_BUYER_PAY", "交易创建:不触发通知"),
    TRADE_CLOSED("TRADE_CLOSED", "交易关闭:触发通知");

    private String code;

    private String msg;

    AlipayTradeStatus(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
