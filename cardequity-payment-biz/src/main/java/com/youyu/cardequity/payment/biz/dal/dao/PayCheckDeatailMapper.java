package com.youyu.cardequity.payment.biz.dal.dao;

import com.youyu.cardequity.common.base.annotation.SpringBean;
import com.youyu.cardequity.payment.biz.dal.entity.PayCheckDeatail;
import org.apache.ibatis.annotations.Param;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 每笔对账信息Mapper
 */
@SpringBean
public interface PayCheckDeatailMapper {

    /**
     * 插入
     *
     * @param payCheckDeatail
     */
    void insertSelective(PayCheckDeatail payCheckDeatail);

    /**
     * 根据交易单号和退款单号为空去查询有可能交易单边的对账数据
     *
     * @param appSheetSerialNo
     * @return
     */
    PayCheckDeatail getByAppSeetSerialNoRefundBatchNoIsNull(@Param("appSheetSerialNo") String appSheetSerialNo);

    /**
     * 根据交易单号和退款批次号查询有可能交易单边的对账数据
     *
     * @param appSheetSerialNo
     * @param payRefundNo
     * @return
     */
    PayCheckDeatail getByAppSeetSerialNoRefundBatchNo(@Param("appSheetSerialNo") String appSheetSerialNo, @Param("payRefundNo") String payRefundNo);

    /**
     * 考虑对账状态是正常的,因为前一天日切导致数据没有(是前一天的日切导致的)
     *
     * @param payCheckDeatail
     */
    void updateByDoTradeAndReturn2BillNotFile(PayCheckDeatail payCheckDeatail);
}
