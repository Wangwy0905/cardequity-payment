package com.youyu.cardequity.payment.enums;

import com.youyu.cardequity.common.base.api.CardequityIBaseResultCode;
import com.youyu.common.constant.ResultCodeConstant;

import static com.youyu.cardequity.common.base.constant.CodeConstant.CODE_PREFIX;
import static com.youyu.cardequity.common.base.constant.CodeConstant.PAYMENT_CENTER;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月12日 下午10:00:00
 * @work 支付响应码
 */
public enum PaymentResultCodeEnum implements CardequityIBaseResultCode {

    SUCCESS(ResultCodeConstant.SUCCESS, "success") {
        @Override
        public String getCode() {
            return ResultCodeConstant.SUCCESS;
        }
    },
    SYSTEM_ERROR(ResultCodeConstant.SYSTEM_ERROR, "系统错误") {
        @Override
        public String getCode() {
            return ResultCodeConstant.SYSTEM_ERROR;
        }
    },

    ORDER_NO_CANNOT_EMPTY("0001", "订单号不能为空!"),
    ORDER_NO_DIGITS("0002", "订单号不能超过{0}位!"),
    ORDER_AMOUNT_RANGE("0003", "订单金额只能在开区间{0}和{1}之间!"),
    ORDER_KEY_INFORMATION_CANNOT_EMPTY("0004", "订单标题等关键信息不能为空!"),
    ORDER_ITEM_MAIN_TYPE_CANNOT_EMPTY("0005", "订单商品主类型不能为空,且只能是0:虚拟类商品或者1:实物类商品!"),
    PAYMENT_CHANNEL_STATE_NUMERATION_ABNORMAL("0006", "未查询到支付渠道状态code:{0}所对应的支付渠道状态枚举!"),
    PAYMENT_CHANNEL_STATUS_IS_ABNORMAL("0007", "支付渠道:{0}的支付状态:{1}不能进行支付操作!"),
    //ALIPAY_TRANSACTION_CLOSED_EXCEPTION("0008", "订单对应的支付宝交易关闭异常!"),
    PAYMENT_SUCCESS_ORDER_CANNOT_CLOSED("0009", "支付成功的订单不能关闭!"),
    PAYMENT_SUCCESS_ORDER_CANNOT_REPETITION_PAY("0010", "支付成功的订单号不能再次支付!"),
    REFUND_ORDER_NO_PAYMENT_ABNORMAL("0011", "退款原支付订单支付信息异常!"),
    SUCCESS_ORDER_PAYMENT_CAN_REFUND("0012", "支付成功的订单才可以退款!"),
    REFUND_AMOUNT_CANNOT_GREATER_PAYMENT_AMOUNT("0013", "退款金额不能大于支付金额!"),
    REFUND_SUCC_CANNOT_REFUNDED("0014", "退款成功的订单不能再次退款!"),
    REFUND_NO_NOT_EXIST("0015", "退款单号不存在!"),
    ALIPAY_BILL_DOWNLOAD_FAILED("0016", "支付宝对账单下载失败!"),
    ALIPAY_BILL_DOWNLOAD_URL_FAILED("0017", "支付宝对账单获取下载地址失败!"),
    ALIPAY_DAY_CUT_CANNOT_NULL("0018", "获取支付宝日切参数对应的时间不能为null!");

    private String code;

    private String desc;

    PaymentResultCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return CODE_PREFIX + PAYMENT_CENTER + code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
