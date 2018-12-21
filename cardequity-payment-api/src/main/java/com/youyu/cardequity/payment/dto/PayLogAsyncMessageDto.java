package com.youyu.cardequity.payment.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 支付日志异步发送支付消息通知交易系统
 */
@Data
@ApiModel
public class PayLogAsyncMessageDto implements Serializable {

    private static final long serialVersionUID = 7935265754362478608L;

    @ApiModelProperty(value = "支付流水号")
    private String payLogId;

    @ApiModelProperty(value = "订单号")
    private String appSheetSerialNo;

    @ApiModelProperty(value = "支付状态:PayLogResponseDto对象状态定义")
    private String payState;
}
