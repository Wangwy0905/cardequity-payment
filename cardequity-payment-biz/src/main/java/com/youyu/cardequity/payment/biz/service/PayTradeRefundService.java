package com.youyu.cardequity.payment.biz.service;

import com.youyu.cardequity.payment.dto.PayTradeRefundDto;
import com.youyu.cardequity.payment.dto.PayTradeRefundResponseDto;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 退款管理Service
 */
public interface PayTradeRefundService {

    /**
     * 退款
     *
     * @param tradeRefundApplyDto
     */
    PayTradeRefundResponseDto tradeRefund(PayTradeRefundDto tradeRefundApplyDto);

}
