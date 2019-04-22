package com.youyu.cardequity.payment.api;

import com.youyu.cardequity.payment.dto.SqlQueryEngineResponseDto;
import com.youyu.common.api.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2019年4月22日 10:00:00
 * @work sql查询引擎 Api
 */
@Api(tags = "sql查询引擎Api")
@FeignClient(name = "cardequity-payment")
@RequestMapping(path = "/sqlQueryEngine")
public interface SqlQueryEngineApi {

    /**
     * 根据map查询sql相关信息
     *
     * @param paramMap
     * @return
     */
    @ApiOperation(value = "sql查询")
    @PostMapping("/getSqlQueryEngine")
    Result<SqlQueryEngineResponseDto> getSqlQueryEngine(@RequestBody Map<String, String> paramMap);
}
