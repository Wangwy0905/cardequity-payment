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
     * this.checkNum = this.checkNum + 1;
     * this.localState = STATUS_PAYMENT_FAIL;
     * this.localPayState = STATUS_PAYMENT_FAIL;
     * this.fileStatus = STATUS_PAYMENT_FAIL;
     * // TODO: 2018/12/31
     * //考虑对账状态是正常的,因为前一天日切导致数据没有(是前一天的日切导致的)
     * this.checkStatus = "";
     * this.backFlag = NOT_NEED_REFUND.getCode();
     */
    void updateByDoTrade2BillNotFile(PayCheckDeatail payCheckDeatail);

    /**
     * 根据交易单号和退款批次号查询有可能交易单边的对账数据
     *
     * @param appSheetSerialNo
     * @param payRefundNo
     * @return
     */
    PayCheckDeatail getByAppSeetSerialNoRefundBatchNo(@Param("appSheetSerialNo") String appSheetSerialNo, @Param("payRefundNo") String payRefundNo);

    /**
     * this.checkNum = this.checkNum + 1;
     * <p>
     * this.localState = STATUS_FAIL;
     * this.localPayState = STATUS_FAIL;
     * this.fileStatus = STATUS_FAIL;
     * // TODO: 2018/12/31
     * //考虑对账状态是正常的,因为前一天日切导致数据没有(是前一天的日切导致的,连续两天都没有)
     * this.checkStatus = "";
     * this.backFlag = NOT_NEED_REFUND.getCode();
     */
    void updateByDoTrade2BillRefundNotFile(PayCheckDeatail payCheckDeatail);
}
