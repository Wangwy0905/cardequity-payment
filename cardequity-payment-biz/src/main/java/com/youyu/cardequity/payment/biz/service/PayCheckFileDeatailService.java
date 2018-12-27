package com.youyu.cardequity.payment.biz.service;

import com.youyu.cardequity.payment.dto.PayCheckFileDeatailDto;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 对账文件明细信息Service
 */
public interface PayCheckFileDeatailService {
    /**
     * 下载对账单并解析入库
     *
     * @param payCheckFileDeatailDto
     */
    void downloadBill(PayCheckFileDeatailDto payCheckFileDeatailDto);

    /**
     * 同步交易订单消息数据
     *
     * @param json
     */
    void syncTradeOrderMessage(String json);
}
