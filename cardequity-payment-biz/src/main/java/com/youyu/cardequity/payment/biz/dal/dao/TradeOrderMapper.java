package com.youyu.cardequity.payment.biz.dal.dao;

import com.youyu.cardequity.payment.biz.dal.entity.TradeOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 交易订单退款信息Mapper
 */
public interface TradeOrderMapper {

    /**
     * 插入
     *
     * @param tradeOrder
     */
    void insertSelective(TradeOrder tradeOrder);

    // TODO: 2018/12/30 记得写sql
    TradeOrder getByAppSeetSerialNoPayRefundNoIsNull(@Param("appSeetSerialNo") String appSeetSerialNo);

    // TODO: 2018/12/30 记得写sql
    TradeOrder getByAppSeetSerialNoPayRefundNo(@Param("appSeetSerialNo") String appSeetSerialNo, @Param("refundBatchNo") String refundBatchNo);

    // TODO: 2018/12/30 记得写sql
    List<TradeOrder> getByCreateNotReconciliation();


    void updateByDoTrade2BillNotFile(TradeOrder tradeOrder);
}
