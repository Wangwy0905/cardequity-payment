package com.youyu.cardequity.payment.dto;

import com.youyu.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 交易订单数据同步
 */
@Data
@ApiModel
public class TradeOrderDto extends BaseDto<String> {

    private static final long serialVersionUID = 2809617038877290187L;

    @ApiModelProperty(value = "订单编号")
    private String appSheetSerialNo;

    @ApiModelProperty(value = "交易账号")
    private String transAccountId;

    @ApiModelProperty(value = "客户号")
    private String clientId;

    @ApiModelProperty(value = "客户姓名")
    private String clientName;

    @ApiModelProperty(value = "支付渠道号")
    private String payChannelNo;

    @ApiModelProperty(value = "支付编号")
    private String payLogId;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderAmount;

    @ApiModelProperty(value = "支付状态")
    private String payState;

    @ApiModelProperty(value = "交易系统退款单号")
    private String payRefundNo;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundAmount;

    @ApiModelProperty(value = "退款状态")
    private String refundStatus;

    @ApiModelProperty(value = "支付系统退款流水号")
    private String payRefundId;

    @ApiModelProperty(value = "业务类型")
    private String businCode;
}
