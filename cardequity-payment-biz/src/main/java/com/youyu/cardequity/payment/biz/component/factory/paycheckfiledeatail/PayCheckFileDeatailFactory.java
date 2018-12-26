package com.youyu.cardequity.payment.biz.component.factory.paycheckfiledeatail;

import com.youyu.cardequity.payment.dto.PayCheckFileDeatailDto;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 对账文件明细信息对账工厂
 */
public abstract class PayCheckFileDeatailFactory {

    /**
     * 对账文件创建工厂
     *
     * @param payCheckFileDeatailDto
     */
    public abstract void createPayCheckFileDeatail(PayCheckFileDeatailDto payCheckFileDeatailDto);
}
