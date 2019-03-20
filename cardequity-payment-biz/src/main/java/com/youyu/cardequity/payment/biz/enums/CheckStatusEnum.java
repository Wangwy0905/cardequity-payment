package com.youyu.cardequity.payment.biz.enums;

import lombok.Getter;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 对账状态枚举
 */
@Getter
public enum CheckStatusEnum {

    UNTREATED("0", "未处理"),
    NORMAL("1", "正常"),
    INCONSISTENT_STATE("2", "状态不符"),
    TRADE_UNILATERAL("3", "交易单边"),
    PAY_UNILATERAL("4", "支付单边"),
    FILE_UNILATERAL("5", "文件单边"),
    OTHER_EXCEPTIONS("6", "其他异常"),
    INVALID("7", "作废"),
    REFUNDED("8", "已退款"),
    MAY_BE_TRADE_UNILATERAL("9", "可能交易单边:交易有,文件没有,可能日切导致的!"),
    MAY_BE_FILE_UNILATERAL("10", "可能文件单边:文件有,交易没有或者退款没有!"),
    REFUND_UNILATERAL("11", "退款单边"),
    MAY_BE_REFUND_UNILATERAL("12", "可能退款单边:退款有,文件没有,可能日切导致的!"),
    RECHARGE_UNILATERAL("13", "充值单边"),
    MAY_BE_RECHARGE_UNILATERAL("14", "可能充值单边");

    private String code;

    private String msg;

    CheckStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
