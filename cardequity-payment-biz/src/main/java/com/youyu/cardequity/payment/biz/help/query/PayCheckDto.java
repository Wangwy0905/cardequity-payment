package com.youyu.cardequity.payment.biz.help.query;

import com.youyu.cardequity.common.base.annotation.SqlQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2019年5月23日 10:00:00
 * @work 支付对账Dto
 */
@SqlQuery(name = "payCheck")
@ApiModel("支付对账Dto")
@Setter
@Getter
public class PayCheckDto implements Serializable {

    private static final long serialVersionUID = -2528937748195206847L;

    @ApiModelProperty("对账日期")
    private LocalDateTime checkTime;

    @ApiModelProperty("对账状态")
    private String checkStatus;

    @ApiModelProperty("支付编号")
    private String payId;

    @ApiModelProperty("渠道支付编号")
    private String channelPaymentNo;

    @ApiModelProperty("生单时间")
    private LocalDateTime applyOrderTime;

    @ApiModelProperty("订单金额")
    private BigDecimal orderAmount;

    @ApiModelProperty("支付金额")
    private BigDecimal payAmount;

    @ApiModelProperty("渠道金额")
    private BigDecimal channalAmount;

    @ApiModelProperty("支付方式")
    private String payType;
}
