package com.youyu.cardequity.payment.biz.controller;

import com.youyu.cardequity.payment.api.PayTradeRefundApi;
import com.youyu.cardequity.payment.biz.service.PayTradeRefundService;
import com.youyu.cardequity.payment.dto.PayTradeRefundDto;
import com.youyu.cardequity.payment.dto.PayTradeRefundResponseDto;
import com.youyu.common.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.youyu.common.api.Result.ok;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 退款管理controller
 */
@RestController
@RequestMapping(value = "/payRefund")
public class PayTradeRefundController implements PayTradeRefundApi {

    @Autowired
    private PayTradeRefundService payRefundService;

    @Override
    @PostMapping(value = "/tradeRefund")
    public Result<PayTradeRefundResponseDto> tradeRefund(@RequestBody PayTradeRefundDto tradeRefundApplyDto) {
        return ok(payRefundService.tradeRefund(tradeRefundApplyDto));
    }

    @Override
    @PostMapping(value = "/getTradeRefund")
    public Result<PayTradeRefundResponseDto> getTradeRefund(@RequestBody PayTradeRefundDto tradeRefundApplyDto) {
        return ok(payRefundService.getTradeRefund(tradeRefundApplyDto));
    }
}
