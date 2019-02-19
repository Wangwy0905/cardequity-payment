package com.youyu.cardequity.payment.biz.help.constant;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Alipay支付常量类
 */
public final class AlipayConstant {

    /**
     * 支付宝支付名称:用于对账区分数据
     */
    public static final String ALIPAY_NAME = "支付宝";
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
     * 支付宝App支付销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
     */
    public static final String ALIPAY_PRODUCT_CODE = "QUICK_MSECURITY_PAY";
    /**
     * 支付宝手机网站支付销售产品码，商家和支付宝签约的产品码，为固定值QUICK_WAP_WAY
     */
    public static final String ALIPAY_MOBILE_WEB_PRODUCT_CODE = "QUICK_WAP_WAY";
    /**
     * 支付宝对账文件名称
     */
    public static final String ALIPAY_BILL_FILE_NAME = "业务明细.csv";
    /**
     * fund_change表示本次退款是否发生了资金变化
     * 返回“Y”表示退款成功
     */
    public static final String ALIPAY_FUND_CHANGE_Y = "Y";
    /**
     * fund_change表示本次退款是否发生了资金变化
     * 返回“N”则表示退款未成功
     */
    public static final String ALIPAY_FUND_CHANGE_N = "N";
    /**
     * 支付宝对账单业务类型:交易
     */
    public static final String ALIPAY_BILL_BUSIN_TYPE_TRADE = "交易";
    /**
     * 支付宝对账单业务类型:退款
     */
    public static final String ALIPAY_BILL_BUSIN_TYPE_REFUND = "退款";

}
