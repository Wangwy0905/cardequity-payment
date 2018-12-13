package com.youyu.cardequity.payment.biz.service;

import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.dto.PayLogDto;
import com.youyu.cardequity.payment.dto.alipay.AlipaySyncMessageDto;
import com.youyu.cardequity.payment.dto.alipay.AlipayTradeCloseDto;
import com.youyu.common.api.Result;
import com.youyu.common.dto.BaseDto;
import com.youyu.common.service.IService;

import java.util.Map;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work PayLog支付日志Service
 */
public interface PayLogService extends IService<PayLogDto, PayLog> {

    /**
     * 支付方法
     * payChannelNo为空则走三方支付路由,快捷支付,后续考虑
     *
     * @param payLogDto
     * @param payLogService
     * @param <T>
     * @return
     */
    <T extends BaseDto> T alipay(PayLogDto payLogDto, PayLogService payLogService);

    /**
     * 保存支付数据
     *
     * @param payLog
     */
    void save(PayLog payLog);

    /**
     * 执行支付
     *
     * @param id
     * @param <T>
     * @return
     */
    <T extends BaseDto> T doPay(String id);

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
     * 支付宝交易关闭
     *
     * @param alipayTradeCloseDto
     * @return
     */
    Result alipayTradeClose(AlipayTradeCloseDto alipayTradeCloseDto);

    /**
     * 定时任务查询支付宝交易
     */
    void timeAlipayTradeQuery();
}
