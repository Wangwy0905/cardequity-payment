package com.youyu.cardequity.payment.biz.enums;

import lombok.Getter;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 退款处理标识枚举
 */
@Getter
public enum BackFlagEnum {

    NOT_NEED_REFUND("0", "无需退款"),
    NEED_REFUND("1", "需退款"),
    REFUNDED("2", "已退款");

    private String code;

    private String msg;

    BackFlagEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
