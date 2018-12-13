package com.youyu.cardequity.payment.dto.alipay;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月8日 下午10:00:00
 * @work 支付宝同步通知结果中的result中的alipay_trade_app_pay_response部分
 */
@Data
@ApiModel
public class AlipaySyncMessageResponseDto implements Serializable {

    private static final long serialVersionUID = -8289445685715786398L;

    @ApiModelProperty(value = "公共错误码:10000调用成功")
    private String code;

    @ApiModelProperty(value = "处理结果的描述，信息来自于code返回结果的描述")
    private String msg;

    @JsonProperty(value = "app_id")
    @ApiModelProperty(value = "支付宝分配给开发者的应用Id")
    private String appId;

    @JsonProperty(value = "out_trade_no")
    @ApiModelProperty(value = "商户网站唯一订单号")
    private String outTradeNo;

    @JsonProperty(value = "trade_no")
    @ApiModelProperty(value = "该交易在支付宝系统中的交易流水号。最长64位。")
    private String tradeNo;

    @JsonProperty(value = "total_amount")
    @ApiModelProperty(value = "该笔订单的资金总额，单位为RMB-Yuan")
    private String totalAmount;

    @JsonProperty(value = "seller_id")
    @ApiModelProperty(value = "收款支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字")
    private String sellerId;

    @ApiModelProperty(value = "编码格式")
    private String charset;

    @ApiModelProperty(value = "时间")
    private String timestamp;

}
