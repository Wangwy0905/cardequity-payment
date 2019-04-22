package com.youyu.cardequity.payment.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2019年4月22日 10:00:00
 * @work sql查询引擎响应dto
 */
@Setter
@Getter
@ApiModel("sql查询引擎响应dto")
public class SqlQueryEngineResponseDto implements Serializable {

    private static final long serialVersionUID = -65192439157318470L;

    @ApiModelProperty("实际sql")
    private String realSql;

    @ApiModelProperty("dto对应的名称")
    private String dtoName;
}
