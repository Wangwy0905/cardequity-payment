package com.youyu.cardequity.payment.biz.service;

import com.youyu.cardequity.payment.dto.PayLogDto;
import com.youyu.cardequity.payment.dto.PayLogResponseDto;
import com.youyu.cardequity.payment.dto.TradeCloseDto;
import com.youyu.cardequity.payment.dto.alipay.AlipaySyncMessageDto;
import com.youyu.common.api.Result;

import java.util.Map;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work PayLog支付日志Service
 */
public interface PayLogService {

    /**
     * 支付方法
     * payChannelNo为空则走三方支付路由,快捷支付,后续考虑
     *
     * @param payLogDto
     * @return
     */
    PayLogResponseDto pay(PayLogDto payLogDto);

    /**
     * 支付宝同步消息通知
     *
     * @param alipaySyncMessageDto
     */
    void alipaySyncMessage(AlipaySyncMessageDto alipaySyncMessageDto);

    /**
     * 支付宝异步消息通知
     *
     * @param params2Map
     * @return
     */
    String alipayAsyncMessage(Map<String, String> params2Map);

    /**
     * 交易关闭
     *
     * @param tradeCloseDto
     * @return
     */
    Result tradeClose(TradeCloseDto tradeCloseDto);

    /**
     * 定时任务查询交易
     */
    void timeTradeQuery();

}
