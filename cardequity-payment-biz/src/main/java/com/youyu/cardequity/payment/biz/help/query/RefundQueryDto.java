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
 * @work 退款查询Dto
 */
@SqlQuery(name = "refundQuery")
@ApiModel("退款查询Dto")
@Setter
@Getter
public class RefundQueryDto implements Serializable {

    private static final long serialVersionUID = -460100653125539258L;

    @ApiModelProperty("退款创建时间")
    private LocalDateTime refundCreateTime;

    @ApiModelProperty("退款编号")
    private String refundId;

    @ApiModelProperty("退款申请金额")
    private BigDecimal refundApplyAmount;

    @ApiModelProperty("退款金额")
    private BigDecimal refundAmount;

    @ApiModelProperty("退款类型")
    private String businCode;

    @ApiModelProperty("退款方式")
    private String refundType;

    @ApiModelProperty("退款状态")
    private String refundStatus;

    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("用户账号")
    private String clientId;
}
