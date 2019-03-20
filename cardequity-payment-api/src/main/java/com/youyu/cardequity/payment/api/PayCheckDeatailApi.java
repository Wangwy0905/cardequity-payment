package com.youyu.cardequity.payment.api;

import com.youyu.cardequity.payment.dto.PayCheckDeatailDto;
import com.youyu.common.api.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月12日 下午10:00:00
 * @work 对账信息管理Api定义
 */
@Api(tags = "对账信息管理Api")
@FeignClient(name = "cardequity-payment")
@RequestMapping(path = "/payCheckDeatail")
public interface PayCheckDeatailApi {

    /**
     * 根据对账单进行对账
     *
     * @param payCheckDeatailDto
     * @return
     */
    @ApiOperation(value = "根据对账单进行对账")
    @PostMapping(value = "/reconciliation")
    Result reconciliation(@RequestBody PayCheckDeatailDto payCheckDeatailDto);
}
