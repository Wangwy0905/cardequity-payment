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
 * @work 退款统计Dto
 */
@SqlQuery(name = "refundStatistics")
@ApiModel("退款统计Dto")
@Setter
@Getter
public class RefundStatisticsDto implements Serializable {

    private static final long serialVersionUID = 3212068567269801674L;

    @ApiModelProperty("退款时间")
    private LocalDateTime refundTime;

    @ApiModelProperty("退款编号")
    private String refundId;

    @ApiModelProperty("退款类型")
    private String businCode;

    @ApiModelProperty("退款申请金额")
    private BigDecimal refundApplyAmount;

    @ApiModelProperty("退款金额")
    private BigDecimal refundAmount;

    @ApiModelProperty("退款方式")
    private String type;

    @ApiModelProperty("退款单号")
    private String refundNo;

    @ApiModelProperty("退款单时间")
    private LocalDateTime orderCreateTime;
}
