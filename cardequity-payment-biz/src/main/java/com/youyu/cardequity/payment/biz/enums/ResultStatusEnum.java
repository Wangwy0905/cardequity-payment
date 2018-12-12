package com.youyu.cardequity.payment.biz.enums;

import lombok.Getter;

import static com.youyu.cardequity.common.base.util.StringUtil.eq;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 支付宝同步通知结果码
 */
@Getter
public enum ResultStatusEnum {
    PAYMENT_SUCC("9000", "订单支付成功") {
        @Override
        public boolean paySucc() {
            return true;
        }
    },
    PROCESSING("8000", "正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态"),
    PAYMENT_FAIL("4000", "订单支付失败"),
    REPETITION_REQUEST("5000", "重复请求"),
    MIDWAY_CANCEL("6001", "用户中途取消"),
    NETWORK_CONNECT_ERROR("6002", "网络连接出错"),
    PAYMENT_UNKNOWN("6004", "支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态"),
    OTHER("其它", "其它支付错误");

    private String code;

    private String msg;

    ResultStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultStatusEnum getResultStatusEnum(String code) {
        ResultStatusEnum[] resultStatusEnums = ResultStatusEnum.values();
        for (ResultStatusEnum resultStatusEnum : resultStatusEnums) {
            if (eq(resultStatusEnum.getCode(), code)) {
                return resultStatusEnum;
            }
        }
        return OTHER;
    }

    public boolean paySucc() {
        return false;
    }
}
