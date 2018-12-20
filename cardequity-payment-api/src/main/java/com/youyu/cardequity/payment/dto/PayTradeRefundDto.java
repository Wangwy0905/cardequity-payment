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
 * @work 退款申请DTO
 */
@Data
@ApiModel
public class PayTradeRefundDto extends BaseDto<String> {

    private static final long serialVersionUID = 1599386415577897472L;

    @ApiModelProperty(value = "退款单号")
    private String id;

    @ApiModelProperty(value = "订单编号")
    private String appSheetSerialNo;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundAmount;

    @ApiModelProperty(value = "退款编号")
    private String refundNo;

    @ApiModelProperty(value = "退款原因")
    private String refundReason;

}
