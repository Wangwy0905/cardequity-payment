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
 * @work 支付统计Dto
 */
@SqlQuery(name = "payStatistics")
@ApiModel("支付统计Dto")
@Setter
@Getter
public class PayStatisticsDto implements Serializable {

    private static final long serialVersionUID = 785391869190040603L;

    @ApiModelProperty("支付创建时间")
    private LocalDateTime payCreateTime;

    @ApiModelProperty("支付编号")
    private String payId;

    @ApiModelProperty("业务代码")
    private String businCode;

    @ApiModelProperty("支付金额")
    private BigDecimal payAmount;

    @ApiModelProperty("支付时间")
    private LocalDateTime payTime;

    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("订单创建时间")
    private LocalDateTime orderCreateTime;
}
