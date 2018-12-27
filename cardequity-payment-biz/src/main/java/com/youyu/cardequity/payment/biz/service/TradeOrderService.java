package com.youyu.cardequity.payment.biz.service;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 交易订单退款信息Service
 */
public interface TradeOrderService {

    /**
     * 同步交易订单消息数据
     *
     * @param json
     */
    void syncTradeOrderMessage(String json);
}
