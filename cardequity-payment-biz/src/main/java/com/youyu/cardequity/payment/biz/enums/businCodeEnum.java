package com.youyu.cardequity.payment.biz.enums;

import lombok.Getter;

import static com.youyu.cardequity.payment.dto.PayLogDto.BUSIN_CODE_MEMBER_RECHARGE;
import static com.youyu.cardequity.payment.dto.PayLogDto.BUSIN_CODE_TRADE;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 业务类型枚举
 */
@Getter
public enum businCodeEnum {

    TRADE(BUSIN_CODE_TRADE, "交易类型"),
    MEMBER_RECHARGE(BUSIN_CODE_MEMBER_RECHARGE, "会员充值");

    private String code;

    private String msg;

    businCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
