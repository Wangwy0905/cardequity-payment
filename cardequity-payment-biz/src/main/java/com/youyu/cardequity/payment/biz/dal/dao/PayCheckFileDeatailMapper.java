package com.youyu.cardequity.payment.biz.dal.dao;

import com.youyu.cardequity.common.base.annotation.SpringBean;
import com.youyu.cardequity.payment.biz.dal.entity.PayCheckFileDeatail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 对账文件明细信息Mapper
 */
@SpringBean
public interface PayCheckFileDeatailMapper {

    /**
     * 根据三方交易号删除
     *
     * @param payCheckFileDeatail
     */
    void deleteByTranceNoCheckDate(PayCheckFileDeatail payCheckFileDeatail);

    /**
     * 插入
     *
     * @param payCheckFileDeatail
     */
    void insertSelective(PayCheckFileDeatail payCheckFileDeatail);

    /**
     * 根据对账日期查询
     *
     * @param billDate
     * @return
     */
    List<PayCheckFileDeatail> getListByBillDate(@Param("billDate") String billDate);

    /**
     * 根据单号查询退款编号为空的对账单
     *
     * @param appSheetSerialNo
     * @return
     */
    PayCheckFileDeatail getByAppSeetSerialNoRefundBatchNoIsNull(@Param("appSheetSerialNo") String appSheetSerialNo);

    /**
     * 根据单号和退款编号查询对账单
     *
     * @param appSheetSerialNo
     * @param refundBatchNo
     * @return
     */
    PayCheckFileDeatail getByAppSeetSerialNoRefundBatchNo(@Param("appSheetSerialNo") String appSheetSerialNo, @Param("refundBatchNo") String refundBatchNo);

    /**
     * 根据id更新对账id,表面已对过账
     *
     * @param id
     * @param payCheckDeatailId
     */
    void updatePayCheckDeatailIdById(@Param("id") String id, @Param("payCheckDeatailId") String payCheckDeatailId);
}
