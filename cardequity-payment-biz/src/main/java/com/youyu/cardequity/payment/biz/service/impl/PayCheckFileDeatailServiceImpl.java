package com.youyu.cardequity.payment.biz.service.impl;

import com.youyu.cardequity.payment.biz.dal.dao.PayChannelInfoMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayChannelInfo;
import com.youyu.cardequity.payment.biz.service.PayCheckFileDeatailService;
import com.youyu.cardequity.payment.dto.PayCheckFileDeatailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 对账文件明细信息Service Impl
 */
@Service
public class PayCheckFileDeatailServiceImpl implements PayCheckFileDeatailService {

    @Autowired
    private PayChannelInfoMapper payChannelInfoMapper;

    @Override
    public void downloadBill(PayCheckFileDeatailDto payCheckFileDeatailDto) {
        String channelNo = payCheckFileDeatailDto.getChannelNo();
        PayChannelInfo payChannelInfo = payChannelInfoMapper.getById(channelNo);
        payChannelInfo.downloadBill(payCheckFileDeatailDto);
    }

    @Override
    public void syncTradeOrderMessage(String json) {
    }
}
