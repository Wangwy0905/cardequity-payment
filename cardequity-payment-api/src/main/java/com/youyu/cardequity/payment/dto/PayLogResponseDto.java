package com.youyu.cardequity.payment.dto;

import com.youyu.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 支付返回结果Dto
 */
@Data
@ApiModel
public class PayLogResponseDto extends BaseDto<String> {

    private static final long serialVersionUID = 39561118107733664L;

    /**
     * 未支付
     */
    public static final String STATUS_NON_PAYMENT = "0";
    /**
     * 支付排队中
     */
    public static final String STATUS_PAYMENT_QUEUING = "1";
    /**
     * 支付中
     */
    public static final String STATUS_PAYMENTING = "2";
    /**
     * 支付成功
     */
    public static final String STATUS_PAYMENT_SUCC = "3";
    /**
     * 支付失败
     */
    public static final String STATUS_PAYMENT_FAIL = "4";

    @ApiModelProperty(name = "支付状态")
    private String payState;

    @ApiModelProperty(name = "订单预支付标志(0:否,1:是)")
    private String prePayFlag;

    @ApiModelProperty(value = "支付机构返回结果")
    private String respInfo;
}
