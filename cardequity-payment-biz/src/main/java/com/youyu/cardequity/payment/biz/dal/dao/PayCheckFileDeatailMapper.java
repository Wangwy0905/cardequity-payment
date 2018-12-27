package com.youyu.cardequity.payment.biz.dal.dao;

import com.youyu.cardequity.payment.biz.dal.entity.PayCheckFileDeatail;
import org.apache.ibatis.annotations.Param;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 对账文件明细信息Mapper
 */
public interface PayCheckFileDeatailMapper {

    /**
     * 根据三方交易号删除
     *
     * @param tranceNo
     */
    void deleteByTranceNo(@Param("tranceNo") String tranceNo);

    /**
     * 插入
     *
     * @param payCheckFileDeatail
     */
    void insertSelective(PayCheckFileDeatail payCheckFileDeatail);
}
