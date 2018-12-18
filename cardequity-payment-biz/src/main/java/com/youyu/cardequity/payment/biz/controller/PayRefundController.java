package com.youyu.cardequity.payment.biz.controller;

import com.youyu.cardequity.payment.api.PayRefundApi;
import com.youyu.cardequity.payment.biz.service.PayRefundService;
import com.youyu.cardequity.payment.dto.TradeRefundApplyDto;
import com.youyu.common.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 退款管理controller
 */
@RestController
@RequestMapping(value = "/payRefund")
public class PayRefundController implements PayRefundApi {

    @Autowired
    private PayRefundService payRefundService;

    @Override
    public Result tradeRefund(@RequestBody TradeRefundApplyDto tradeRefundApplyDto) {
        payRefundService.tradeRefund(tradeRefundApplyDto);
        return null;
    }
}
