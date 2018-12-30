package com.youyu.cardequity.payment.biz.dal.dao;

import com.youyu.cardequity.payment.biz.dal.entity.PayCheckDeatail;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 每笔对账信息Mapper
 */
public interface PayCheckDeatailMapper {

    /**
     * 插入
     *
     * @param payCheckDeatail
     */
    void insertSelective(PayCheckDeatail payCheckDeatail);
}
