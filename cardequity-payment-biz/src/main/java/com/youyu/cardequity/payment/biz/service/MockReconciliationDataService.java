package com.youyu.cardequity.payment.biz.service;

import com.youyu.cardequity.payment.dto.MockReconciliationDataDto;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work mock对账造数据方便测试测 Service
 */
public interface MockReconciliationDataService {

    /**
     * mock交易数据
     *
     * @param mockReconciliationDataDto
     */
    void mockTradeData(MockReconciliationDataDto mockReconciliationDataDto);

    /**
     * mock退款数据
     *
     * @param mockReconciliationDataDto
     */
    void mockRefundData(MockReconciliationDataDto mockReconciliationDataDto);
}
