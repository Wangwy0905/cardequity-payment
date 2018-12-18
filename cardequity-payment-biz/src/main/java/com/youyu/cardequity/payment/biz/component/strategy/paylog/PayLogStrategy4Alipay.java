package com.youyu.cardequity.payment.biz.component.strategy.paylog;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.component.properties.AlipayProperties;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog4Alipay;
import com.youyu.cardequity.payment.dto.AlipayPrepayment4PayLogDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.youyu.cardequity.payment.biz.help.constant.Constant.ALIPAY_PRODUCT_CODE;
import static org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace;
import static org.apache.commons.lang3.StringUtils.isNoneBlank;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Alipay Pay支付宝支付策略
 */
@Slf4j
@StatusAndStrategyNum(superClass = PayLogStrategy.class, number = "1", describe = "支付宝支付策略")
@Component
public class PayLogStrategy4Alipay extends PayLogStrategy {

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private AlipayClient alipayClient;

    @Override
    public AlipayPrepayment4PayLogDto executePay(PayLog payLog) {
        PayLog4Alipay payLog4Alipay = (PayLog4Alipay) payLog;
        AlipayTradeAppPayRequest alipayTradeAppPayRequest = getAlipayTradeAppPayRequest(payLog4Alipay);
        try {
            AlipayTradeAppPayResponse alipayTradeAppPayResponse = alipayClient.sdkExecute(alipayTradeAppPayRequest);
            String syncResponseBody = alipayTradeAppPayResponse.getBody();
            return payLog4Alipay.prepaymentSucc(syncResponseBody);
        } catch (AlipayApiException ex) {
            log.error("调用支付宝预支付的支付编号:[{}]和异常信息:[{}]", payLog4Alipay.getId(), getFullStackTrace(ex));
            return payLog4Alipay.prepaymentFail("调用支付宝预支付信息异常!");
        }
    }

    private AlipayTradeAppPayRequest getAlipayTradeAppPayRequest(PayLog4Alipay payLog4Alipay) {
        AlipayTradeAppPayRequest alipayTradeAppPayRequest = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel alipayTradeAppPayModel = getAlipayTradeAppPayModel(payLog4Alipay);
        alipayTradeAppPayRequest.setBizModel(alipayTradeAppPayModel);
        alipayTradeAppPayRequest.setNotifyUrl(alipayProperties.getNotifyUrl());
        return alipayTradeAppPayRequest;
    }

    private AlipayTradeAppPayModel getAlipayTradeAppPayModel(PayLog4Alipay payLog4Alipay) {
        AlipayTradeAppPayModel alipayTradeAppPayModel = new AlipayTradeAppPayModel();
        alipayTradeAppPayModel.setSubject(payLog4Alipay.getAlipaySubject());
        alipayTradeAppPayModel.setOutTradeNo(payLog4Alipay.getAppSheetSerialNo());
        if (isNoneBlank(payLog4Alipay.getAlipayTimeoutExpress())) {
            alipayTradeAppPayModel.setTimeoutExpress(payLog4Alipay.getAlipayTimeoutExpress());
        }
        alipayTradeAppPayModel.setTotalAmount(payLog4Alipay.getOccurBalance().toString());
        alipayTradeAppPayModel.setProductCode(ALIPAY_PRODUCT_CODE);
        return alipayTradeAppPayModel;
    }
}
