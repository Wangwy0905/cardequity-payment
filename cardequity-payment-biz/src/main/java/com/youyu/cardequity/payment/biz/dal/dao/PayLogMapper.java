package com.youyu.cardequity.payment.biz.dal.dao;

import com.youyu.cardequity.common.base.annotation.SpringBean;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 支付日志Mapper
 */
@SpringBean
public interface PayLogMapper {

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    PayLog getById(String id);

    /**
     * 更新支付宝预支付信息:主要更新状态和返回信息
     *
     * @param payLog
     */
    void updateAlipayPrepayment(PayLog payLog);

    /**
     * 更新支付宝同步通知信息:主要更新同步消息
     *
     * @param payLog
     */
    void updateAlipaySyncMessage(PayLog payLog);

    /**
     * 更新支付宝异步通知信息:主要更新状态和同步消息
     *
     * @param payLog
     */
    void updateAlipayAsyncMessage(PayLog payLog);

    /**
     * 支付宝交易关闭相关信息:主要更新交易关闭消息
     *
     * @param payLog
     */
    void updateAlipayTradeClose(PayLog payLog);

    /**
     * 支付宝交易查询相关信息:主要更新查询消息状态和交易状态
     *
     * @param payLog
     */
    void updateAlipayTradeQuery(PayLog payLog);

    /**
     * 插入
     *
     * @param payLog
     * @return
     */
    int insertSelective(PayLog payLog);

    /**
     * 根据订单单号查询和路由标志
     *
     * @param appSheetSerialNo
     * @param routeVoIdFlag
     * @return
     */
    PayLog getByAppSheetSerialNoRouteVoIdFlag(@Param("appSheetSerialNo") String appSheetSerialNo, @Param("routeVoIdFlag") String routeVoIdFlag);

    /**
     * 在阈值范围内还未收到支付宝异步回调通知时,主动查询未通知的订单进行主动查询
     *
     * @param alipayAsyncNotifyThresholdStart 时间阈值开始:[当前时间-alipayAsyncNotifyThresholdStart分钟]
     * @param alipayAsyncNotifyThresholdEnd   时间阈值结束:[当前时间-alipayAsyncNotifyThresholdEnd分钟]
     * @return
     */
    List<PayLog> getByTimeTradeQuery(@Param("alipayAsyncNotifyThresholdStart") Integer alipayAsyncNotifyThresholdStart, @Param("alipayAsyncNotifyThresholdEnd") Integer alipayAsyncNotifyThresholdEnd);

    /**
     * 根据单号更新支付路由信息
     *
     * @param appSheetSerialNo
     * @param routeVoIdFlag
     */
    void updateByAppSheetSerialNoRouteVoIdFlag(@Param("appSheetSerialNo") String appSheetSerialNo, @Param("routeVoIdFlag") String routeVoIdFlag);

    /**
     * 更新订单号相同,但支付单号不等于异步回调的支付单号的路由标志
     *
     * @param appSheetSerialNo
     * @param id
     * @param routeVoIdFlag
     */
    void updateAppSheetSerialNo4OtherPayIdRouteVoIdFlag(@Param("appSheetSerialNo") String appSheetSerialNo, @Param("id") String id, @Param("routeVoIdFlag") String routeVoIdFlag);

    /**
     * 盘后文件对交易对账:支付成功
     *
     * @param payLog
     */
    void updateByPayAfterBill2TradeSucc(PayLog payLog);
}
