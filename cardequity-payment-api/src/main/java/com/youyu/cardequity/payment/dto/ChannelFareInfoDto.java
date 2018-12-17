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
 * @work 渠道费率Dto
 */
@Data
@ApiModel
public class ChannelFareInfoDto extends BaseDto<String> {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "渠道号")
    private String channelNo;

    @ApiModelProperty(value = "公司Id")
    private String companyId;

    @ApiModelProperty(value = "金额段起始(不含)")
    private BigDecimal amountLevelStart;

    @ApiModelProperty(value = "金额段结束(含)")
    private BigDecimal amountLevelEnd;

    @ApiModelProperty(value = "最高费用")
    private BigDecimal maxFare;

    @ApiModelProperty(value = "最低费用")
    private BigDecimal minFare;

    @ApiModelProperty(value = "具体费率(%)")
    private BigDecimal fareRatio;

    @ApiModelProperty(value = "固定费用")
    private BigDecimal fixedFare;

    @ApiModelProperty(value = "支持银行")
    private String relationBanks;

    @ApiModelProperty(value = "备注")
    private String remark;

}
