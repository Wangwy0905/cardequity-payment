package com.youyu.cardequity.payment.biz.dal.dao;

import com.youyu.cardequity.payment.biz.dal.entity.PayTradeRefund;
import com.youyu.cardequity.payment.biz.dal.entity.PayTradeRefund4Alipay;
import org.apache.ibatis.annotations.Param;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 退款Mapper
 */
public interface PayTradeRefundMapper {

    /**
     * 根据退款编号交易单号查询
     *
     * @param appSheetSerialNo
     * @param refundNo
     * @return
     */
    PayTradeRefund getByAppSheetSerialNoRefundNo(@Param("appSheetSerialNo") String appSheetSerialNo, @Param("refundNo") String refundNo);

    /**
     * 插入
     *
     * @param payTradeRefund
     */
    void insertSelective(PayTradeRefund payTradeRefund);

    /**
     * 更新退款
     *
     * @param payTradeRefund4Alipay
     */
    void updateByAlipayRefund(PayTradeRefund4Alipay payTradeRefund4Alipay);
}
