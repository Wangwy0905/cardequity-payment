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

    /**
     * 查询交易数据,不包括退款的
     *
     * @param appSeetSerialNo
     * @return
     */
    TradeOrder getByAppSeetSerialNoPayRefundNoIsNull(@Param("appSeetSerialNo") String appSeetSerialNo);

    /**
     * 根据单号和退款编号查询退款数据
     *
     * @param appSeetSerialNo
     * @param refundBatchNo
     * @return
     */
    TradeOrder getByAppSeetSerialNoPayRefundNo(@Param("appSeetSerialNo") String appSeetSerialNo, @Param("refundBatchNo") String refundBatchNo);

    /**
     * 查询未对账的前一天数据和由于日切导致的数据需要继续对账
     *
     * @param syncDataDate
     * @param mayBeTradeUnilateral
     * @param mayBeRefundUnilateral
     * @return
     */
    // TODO: 2019/1/2
    List<TradeOrder> getBySyncDataDateAndDayCut(@Param("syncDataDate") String syncDataDate, @Param("mayBeTradeUnilateral") String mayBeTradeUnilateral, @Param("mayBeRefundUnilateral") String mayBeRefundUnilateral);

    /**
     * 非日切导致的对账没有文件的时候更新支付状态,认为是支付失败的
     *
     * @param tradeOrder
     */
    void updatePayStatusByPayAfter(TradeOrder tradeOrder);

    /**
     * 非日切导致的对账没有文件的时候更新退款状态,认为是退款失败的
     *
     * @param tradeOrder
     */
    void updateReturnStatusByRefundAfter(TradeOrder tradeOrder);
}
