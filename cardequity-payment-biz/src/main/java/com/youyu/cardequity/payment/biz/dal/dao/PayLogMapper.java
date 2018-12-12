package com.youyu.cardequity.payment.biz.dal.dao;

import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.common.mapper.YyMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 支付日志Mapper
 */
public interface PayLogMapper extends YyMapper<PayLog> {

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
     * 更新支付宝同步通知信息:主要更新状态和同步消息
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
     * 支付宝交易关闭相关信息
     *
     * @param payLog
     */
    void updateAlipayTradeClose(PayLog payLog);

    /**
     * 插入
     *
     * @param payLog
     * @return
     */
    @Override
    int insertSelective(PayLog payLog);

    /**
     * 根据订单单号查询
     *
     * @param appSheetSerialNo
     * @return
     */
    PayLog getByAppSheetSerialNo(@Param("appSheetSerialNo") String appSheetSerialNo);

}
