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
 * @work 支付对账单Dto
 */
@SqlQuery(name = "payCheckFile")
@ApiModel("支付对账单Dto")
@Setter
@Getter
public class PayCheckFileDto implements Serializable {

    private static final long serialVersionUID = -3316916834176947144L;

    @ApiModelProperty("下载日期")
    private LocalDateTime downloadTime;

    @ApiModelProperty("渠道号")
    private String channelNo;

    @ApiModelProperty("第三方支付编号")
    private String thirdPaymentNo;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("对账日期")
    private LocalDateTime checkTime;

    @ApiModelProperty("生单日期")
    private LocalDateTime applyOrderTime;

    @ApiModelProperty("申请金额")
    private BigDecimal applyAmount;

    @ApiModelProperty("支付状态")
    private String payStatus;

    @ApiModelProperty("订单编号")
    private String orderId;

}
