package com.youyu.cardequity.payment.biz.enums;

import com.youyu.common.exception.BizException;
import lombok.Getter;

import static com.youyu.cardequity.common.base.util.StringUtil.eq;
import static com.youyu.cardequity.payment.enums.PaymentResultCodeEnum.PAYMENT_CHANNEL_STATE_NUMERATION_ABNORMAL;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 支付渠道状态枚举(不做业务逻辑, 只做判断)
 */
@Getter
public enum PayChannelStateEnum {

    NORMAL("0", "正常") {
        @Override
        public boolean canCreatePayLog() {
            return true;
        }
    },
    UNDER_MAINTENANCE("1", "维护中"),
    DETECTION_ABNORMAL("2", "自动检测异常"),
    PAUSE("3", "暂停");

    private String code;

    private String msg;

    PayChannelStateEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static PayChannelStateEnum getPayChannelStateEnum(String code) {
        PayChannelStateEnum[] payChannelStateEnums = PayChannelStateEnum.values();
        for (PayChannelStateEnum payChannelStateEnum : payChannelStateEnums) {
            if (eq(payChannelStateEnum.getCode(), code)) {
                return payChannelStateEnum;
            }
        }
        throw new BizException(PAYMENT_CHANNEL_STATE_NUMERATION_ABNORMAL.getCode(), PAYMENT_CHANNEL_STATE_NUMERATION_ABNORMAL.getFormatDesc(code));
    }

    public boolean canCreatePayLog() {
        return false;
    }
}
