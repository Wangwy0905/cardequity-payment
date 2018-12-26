package com.youyu.cardequity.payment.api;

import com.youyu.cardequity.payment.dto.PayCheckFileDeatailDto;
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
 * @work 对账文件明细信息Api
 */
@Api(tags = "对账文件明细信息Api")
@FeignClient(name = "cardequity-payment")
@RequestMapping(path = "/payCheckFileDeatail")
public interface PayCheckFileDeatailApi {

    /**
     * 下载对账单
     *
     * @param payCheckFileDeatailDto
     * @return
     */
    @ApiOperation(value = "下载对账单")
    @PostMapping(value = "/downloadBill")
    Result downloadBill(@RequestBody PayCheckFileDeatailDto payCheckFileDeatailDto);
}
