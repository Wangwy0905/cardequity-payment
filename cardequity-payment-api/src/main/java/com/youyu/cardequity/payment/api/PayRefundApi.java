package com.youyu.cardequity.payment.api;

import com.youyu.cardequity.payment.dto.TradeRefundApplyDto;
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
 * @work 退款管理Api定义
 */
@Api(tags = "退款管理Api")
@FeignClient(name = "cardequity-payment")
@RequestMapping(path = "/payRefund")
public interface PayRefundApi {

    /**
     * 退款接口
     *
     * @param tradeRefundApplyDto
     * @return
     */
    @ApiOperation(value = "退款接口")
    @PostMapping(value = "/tradeRefund")
    Result tradeRefund(@RequestBody TradeRefundApplyDto tradeRefundApplyDto);
}
