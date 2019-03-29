package com.youyu.cardequity.payment.biz.component.strategy.paytraderefund;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.component.properties.AlipayProperties;
import com.youyu.cardequity.payment.biz.dal.dao.PayTradeRefundMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayTradeRefund;
import com.youyu.cardequity.payment.biz.dal.entity.PayTradeRefund4Alipay;
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

    @Autowired
    private PayTradeRefundMapper payTradeRefundMapper;

    @Override
    public void executePayTradeRefund(PayTradeRefund payTradeRefund) {
        PayTradeRefund4Alipay payTradeRefund4Alipay = (PayTradeRefund4Alipay) payTradeRefund;
        AlipayTradeRefundRequest alipayTradeRefundRequest = getAlipayTradeRefundRequest(payTradeRefund4Alipay);
        payTradeRefund.callRefund();

        try {
            AlipayTradeRefundResponse alipayTradeRefundResponse = alipayClient.execute(alipayTradeRefundRequest);
            payTradeRefund4Alipay.callRefundSucc(alipayTradeRefundResponse);
        } catch (AlipayApiException ex) {
            log.error("调用支付宝退款编号:[{}]和异常信息:[{}]", payTradeRefund4Alipay.getId(), getFullStackTrace(ex));
            payTradeRefund4Alipay.callRefundException("支付宝退款调用失败错误码:" + ex.getErrCode() + "和错误原因:" + ex.getErrMsg());
        }

        payTradeRefundMapper.updateByAlipayRefund(payTradeRefund4Alipay);
    }

    private AlipayTradeRefundRequest getAlipayTradeRefundRequest(PayTradeRefund4Alipay payTradeRefund4Alipay) {
        AlipayTradeRefundRequest alipayTradeRefundRequest = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel alipayTradeRefundModel = getAlipayTradeRefundModel(payTradeRefund4Alipay);
        alipayTradeRefundRequest.setBizModel(alipayTradeRefundModel);
        return alipayTradeRefundRequest;
    }

    private AlipayTradeRefundModel getAlipayTradeRefundModel(PayTradeRefund4Alipay payTradeRefund4Alipay) {
        AlipayTradeRefundModel alipayTradeRefundModel = new AlipayTradeRefundModel();
        alipayTradeRefundModel.setOutTradeNo(payTradeRefund4Alipay.getAppSheetSerialNo());
        alipayTradeRefundModel.setTradeNo(payTradeRefund4Alipay.getAlipayTradeNo());
        alipayTradeRefundModel.setRefundAmount(payTradeRefund4Alipay.getRefundApplyAmount().toString());
        alipayTradeRefundModel.setOutRequestNo(payTradeRefund4Alipay.getRefundNo());
        alipayTradeRefundModel.setRefundReason(payTradeRefund4Alipay.getRefundReason());
        return alipayTradeRefundModel;
    }

}
