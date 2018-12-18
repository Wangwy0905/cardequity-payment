package com.youyu.cardequity.payment.biz.component.strategy.paytraderefund;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.component.properties.AlipayProperties;
import com.youyu.cardequity.payment.biz.dal.entity.PayRefund;
import com.youyu.cardequity.payment.biz.dal.entity.PayRefund4Alipay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Alipay 退款策略
 */
@Slf4j
@StatusAndStrategyNum(superClass = PayTradeRefundStrategy.class, number = "1", describe = "支付宝退款策略")
@Component
public class PayTradeRefundStrategy4Alipay extends PayTradeRefundStrategy {

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private AlipayClient alipayClient;

    @Override
    public void executePayTradeRefund(PayRefund payTradeRefund) {
        PayRefund4Alipay payTradeRefund4Alipay = (PayRefund4Alipay) payTradeRefund;
        AlipayTradeRefundRequest alipayTradeRefundRequest = getAlipayTradeRefundRequest(payTradeRefund4Alipay);
        try {
            AlipayTradeRefundResponse alipayTradeRefundResponse = alipayClient.execute(alipayTradeRefundRequest);
            // TODO: 2018/12/18  
        } catch (AlipayApiException e) {
            log.error("调用支付宝退款编号:[{}]和异常信息:[{}]", payTradeRefund4Alipay.getId(), getFullStackTrace(e));
        }
    }

    private AlipayTradeRefundRequest getAlipayTradeRefundRequest(PayRefund4Alipay payTradeRefund4Alipay) {
        AlipayTradeRefundRequest alipayTradeRefundRequest = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel alipayTradeRefundModel = getAlipayTradeRefundModel(payTradeRefund4Alipay);
        alipayTradeRefundRequest.setBizModel(alipayTradeRefundModel);
        return alipayTradeRefundRequest;
    }

    private AlipayTradeRefundModel getAlipayTradeRefundModel(PayRefund4Alipay payTradeRefund4Alipay) {
        AlipayTradeRefundModel alipayTradeRefundModel = new AlipayTradeRefundModel();
        alipayTradeRefundModel.setOutTradeNo(payTradeRefund4Alipay.getAppSheetSerialNo());
        alipayTradeRefundModel.setTradeNo(payTradeRefund4Alipay.getAlipayTradeNo());
        alipayTradeRefundModel.setRefundAmount(payTradeRefund4Alipay.getRefundApplyAmount().toString());
        alipayTradeRefundModel.setRefundReason(payTradeRefund4Alipay.getRefundReason());
        return alipayTradeRefundModel;
    }

}
