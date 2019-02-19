package com.youyu.cardequity.payment.biz.component.strategy.paylog;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.component.properties.AlipayProperties;
import com.youyu.cardequity.payment.biz.dal.dao.PayLogMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog4Alipay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.youyu.cardequity.payment.biz.help.constant.AlipayConstant.ALIPAY_MOBILE_WEB_PRODUCT_CODE;
import static org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;
import static org.apache.commons.lang3.StringUtils.isNoneBlank;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Alipay Pay支付宝支付策略
 */
@Slf4j
@StatusAndStrategyNum(superClass = PayLogStrategy.class, number = "2", describe = "支付宝手机网站支付策略")
@Component
public class PayLogStrategy4AlipayMobileWeb extends PayLogStrategy {

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private PayLogMapper payLogMapper;

    @Override
    public void executePay(PayLog payLog) {
        PayLog4Alipay payLog4Alipay = (PayLog4Alipay) payLog;
        AlipayTradeWapPayRequest alipayTradeWapPayRequest = getAlipayTradeWapPayRequest(payLog4Alipay);
        payLog4Alipay.callPay();

        try {
            AlipayTradeWapPayResponse alipayTradeWapPayResponse = alipayClient.pageExecute(alipayTradeWapPayRequest);
            String syncResponseBody = alipayTradeWapPayResponse.getBody();
            payLog4Alipay.callPrepaymentSucc(syncResponseBody);
        } catch (AlipayApiException ex) {
            log.error("调用支付宝手机网站预支付的支付编号:[{}]和异常信息:[{}]", payLog4Alipay.getId(), getFullStackTrace(ex));
            payLog4Alipay.callPrepaymentFail("调用支付宝手机网站预支付信息异常!");
        }
        payLogMapper.updateAlipayPrepayment(payLog4Alipay);
    }

    private AlipayTradeWapPayRequest getAlipayTradeWapPayRequest(PayLog4Alipay payLog4Alipay) {
        AlipayTradeWapPayRequest alipayTradeWapPayRequest = new AlipayTradeWapPayRequest();
        AlipayTradeWapPayModel alipayTradeWapPayModel = getAlipayTradeWapPayModel(payLog4Alipay);
        alipayTradeWapPayRequest.setBizModel(alipayTradeWapPayModel);
        alipayTradeWapPayRequest.setNotifyUrl(alipayProperties.getNotifyUrl());
        return alipayTradeWapPayRequest;
    }

    private AlipayTradeWapPayModel getAlipayTradeWapPayModel(PayLog4Alipay payLog4Alipay) {
        AlipayTradeWapPayModel alipayTradeWapPayModel = new AlipayTradeWapPayModel();
        alipayTradeWapPayModel.setSubject(payLog4Alipay.getAlipaySubject());
        alipayTradeWapPayModel.setOutTradeNo(payLog4Alipay.getAppSheetSerialNo());
        alipayTradeWapPayModel.setTotalAmount(payLog4Alipay.getOccurBalance().toString());
        alipayTradeWapPayModel.setProductCode(ALIPAY_MOBILE_WEB_PRODUCT_CODE);
        if (isNoneBlank(payLog4Alipay.getAlipayTimeoutExpress())) {
            alipayTradeWapPayModel.setTimeoutExpress(payLog4Alipay.getAlipayTimeoutExpress());
        }
        alipayTradeWapPayModel.setGoodsType(defaultIfBlank(payLog4Alipay.getAlipayGoodsType(), "1"));
        return alipayTradeWapPayModel;
    }


}
