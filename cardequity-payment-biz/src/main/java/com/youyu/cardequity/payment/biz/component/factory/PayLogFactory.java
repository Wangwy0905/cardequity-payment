package com.youyu.cardequity.payment.biz.component.factory;

import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.dto.PayLogDto;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Pay支付工厂
 */
public abstract class PayLogFactory {

    /**
     * 支付日志创建工厂
     *
     * @param payLogDto
     * @return
     */
    public abstract PayLog create(PayLogDto payLogDto);
}
