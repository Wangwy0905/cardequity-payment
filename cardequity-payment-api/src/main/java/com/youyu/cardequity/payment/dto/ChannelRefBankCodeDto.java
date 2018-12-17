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
 * @work 渠道银行信息Dto
 */
@Data
@ApiModel
public class ChannelRefBankCodeDto extends BaseDto<String> {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "渠道号")
    private String channelNo;

    @ApiModelProperty(value = "银行代码")
    private String bankCode;

    @ApiModelProperty(value = "支付接口类型")
    private String payInterfaceType;

    @ApiModelProperty(value = "支付模式")
    private String payMode;

    @ApiModelProperty(value = "单日累计限额")
    private BigDecimal perDayUp;

    @ApiModelProperty(value = "单人当日限额")
    private BigDecimal perDayAndPersonUp;

    @ApiModelProperty(value = "单笔限额")
    private BigDecimal maxAmount;

    @ApiModelProperty(value = "连续失败笔数")
    private Integer continuityFailNum;

    @ApiModelProperty(value = "短信发送模式")
    private String msgSendFlag;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "日期统计类型")
    private String dateFlag;

    @ApiModelProperty(value = "路由权重值")
    private Integer weightValue;

    @ApiModelProperty(value = "渠道的银行代码")
    private String channelBankCode;
}
