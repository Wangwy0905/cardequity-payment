package com.youyu.cardequity.payment.biz.help.constant;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 支付系统常量类
 */
public final class Constant {

    /**
     * 支付宝异步通知响应成功
     */
    public static final String ALIPAY_ASYNC_RESPONSE_SUCC = "success";
    /**
     * 支付宝异步通知响应失败
     */
    public static final String ALIPAY_ASYNC_RESPONSE_FAIL = "fail";
    /**
     * 支付宝字符集
     */
    public static final String ALIPAY_CHARSET = "utf-8";
    /**
     * 支付宝签名算法
     */
    public static final String ALIPAY_SIGN_TYPE = "RSA2";
    /**
     * 支付宝订单号key
     */
    public static final String ALIPAY_OUT_TRADE_NO = "out_trade_no";
    /**
     * 支付宝付款金额
     */
    public static final String ALIPAY_TOTAL_AMOUNT = "total_amount";
    /**
     * 支付宝卖家支付宝用户号
     */
    public static final String ALIPAY_SELLER_ID = "seller_id";
    /**
     * 支付宝分配给开发者的应用Id
     */
    public static final String ALIPAY_APP_ID = "app_id";
    /**
     * 支付宝交易状态
     */
    public static final String ALIPAY_TRADE_STATUS = "trade_status";
    /**
     * 支付宝交易号
     */
    public static final String ALIPAY_TRADE_NO = "trade_no";
    /**
     * 支付宝交易完成
     */
    public static final String ALIPAY_TRADE_FINISHED = "TRADE_FINISHED";
    /**
     * 支付宝支付成功
     */
    public static final String ALIPAY_TRADE_SUCCESS = "TRADE_SUCCESS";
    /**
     * 支付宝销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
     */
    public static final String ALIPAY_PRODUCT_CODE = "QUICK_MSECURITY_PAY";


    /**
     * 支付类型
     */
    public static final String ALIPAY_PAY_TYPE = "1";
}
