package com.youyu.cardequity.payment.dto;

import com.youyu.cardequity.common.base.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 商户信息Dto
 */
@Data
@ApiModel
public class CompanyInfoDto extends BasePageQuery<String> {

    @ApiModelProperty(value = "公司Id")
    private String companyId;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @Override
    public String getId() {
        return companyId;
    }

    @Override
    public void setId(String companyId) {
        this.companyId = companyId;
    }
}
