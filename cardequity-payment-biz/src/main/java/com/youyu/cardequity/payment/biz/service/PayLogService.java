package com.youyu.cardequity.payment.biz.service;

import com.youyu.cardequity.payment.dto.PayLogDto;
import com.youyu.cardequity.payment.dto.PayLogResponseDto;
import com.youyu.cardequity.payment.dto.TradeCloseDto;
import com.youyu.cardequity.payment.dto.TradeCloseResponseDto;
import com.youyu.cardequity.payment.dto.alipay.AlipaySyncMessageDto;

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
     * @param payLogService
     * @return
     */
    String alipayAsyncMessage(Map<String, String> params2Map, PayLogService payLogService);

    /**
     * 交易关闭
     *
     * @param tradeCloseDto
     * @return
     */
    TradeCloseResponseDto tradeClose(TradeCloseDto tradeCloseDto);

    /**
     * 定时任务查询交易
     */
    void timeTradeQuery();

    /**
     * 执行异步消息
     *
     * @param params2Map
     * @param payLogId
     * @return
     */
    String doAlipayAsyncMessage(Map<String, String> params2Map, String payLogId);

    /**
     * 发送异步消息通知交易系统
     *
     * @param payLogId
     */
    void doSendAlipayAsyncMessage2Trade(String payLogId);
}
