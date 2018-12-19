package com.youyu.cardequity.payment.biz.service.impl;

import com.youyu.cardequity.payment.biz.dal.dao.PayChannelInfoMapper;
import com.youyu.cardequity.payment.biz.dal.dao.PayLogMapper;
import com.youyu.cardequity.payment.biz.dal.dao.PayRefundMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayChannelInfo;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.dal.entity.PayRefund;
import com.youyu.cardequity.payment.biz.service.PayRefundService;
import com.youyu.cardequity.payment.dto.TradeRefundApplyDto;
import com.youyu.common.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.youyu.cardequity.payment.biz.enums.RouteVoIdFlagEnum.NORMAL;
import static com.youyu.cardequity.payment.enums.PaymentResultCodeEnum.REFUND_ORDER_NO_PAYMENT_ABNORMAL;
import static java.util.Objects.isNull;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 退款管理Service impl
 */
@Service
public class PayRefundServiceImpl implements PayRefundService {

    @Autowired
    private PayLogMapper payLogMapper;
    @Autowired
    private PayRefundMapper payRefundMapper;
    @Autowired
    private PayChannelInfoMapper payChannelInfoMapper;

    @Override
    public void tradeRefund(TradeRefundApplyDto tradeRefundApplyDto) {
        PayLog payLog = getPayLog(tradeRefundApplyDto);
        String payChannelNo = payLog.getPayChannelNo();
        PayChannelInfo payChannelInfo = payChannelInfoMapper.getById(payChannelNo);

        PayRefund payRefund = payChannelInfo.createPayRefundAndRefund(tradeRefundApplyDto, payLog);
    }

    private PayLog getPayLog(TradeRefundApplyDto tradeRefundApplyDto) {
        String appSheetSerialNo = tradeRefundApplyDto.getAppSheetSerialNo();
        PayLog payLog = payLogMapper.getByAppSheetSerialNoRouteVoIdFlag(appSheetSerialNo, NORMAL.getCode());

        if (isNull(payLog)) {
            throw new BizException(REFUND_ORDER_NO_PAYMENT_ABNORMAL);
        }

        return payLog;
    }
}
