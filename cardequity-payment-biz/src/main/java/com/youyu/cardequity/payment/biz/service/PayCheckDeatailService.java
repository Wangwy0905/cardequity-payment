package com.youyu.cardequity.payment.biz.service;

import com.youyu.cardequity.payment.dto.PayCheckDeatailDto;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 对账信息管理 Service
 */
public interface PayCheckDeatailService {

    /**
     * 根据对账单进行对账
     *
     * @param payCheckDeatailDto
     */
    void reconciliation(PayCheckDeatailDto payCheckDeatailDto);
}
