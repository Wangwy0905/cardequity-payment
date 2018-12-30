package com.youyu.cardequity.payment.biz.component.command.paylog;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.dal.dao.PayTradeRefundMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog4Alipay;
import com.youyu.cardequity.payment.biz.dal.entity.PayTradeRefund4Alipay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Alipay 统一收单交易退款查询
 */
@Slf4j
@StatusAndStrategyNum(superClass = PayLogCommond.class, number = "5", describe = "支付宝统一收单交易退款查询")
@Component
public class PayLogCommond4AlipayTradeFastpayRefundQuery extends PayLogCommond {

    @Autowired
    private PayTradeRefundMapper payTradeRefundMapper;

    @Autowired
    private AlipayClient alipayClient;

    /**
     * @param payLog
     * @param t      payTradeRefund4Alipay:退款实体
     * @param <T>
     */
    @Override
    public <T> void executeCmd(PayLog payLog, T t) {
        PayTradeRefund4Alipay payTradeRefund4Alipay = (PayTradeRefund4Alipay) t;
        PayLog4Alipay payLog4Alipay = (PayLog4Alipay) payLog;
        AlipayTradeFastpayRefundQueryRequest alipayTradeFastpayRefundQueryRequest = getAlipayTradeFastpayRefundQueryRequest(payLog, payTradeRefund4Alipay);
        try {
            AlipayTradeFastpayRefundQueryResponse alipayTradeFastpayRefundQueryResponse = alipayClient.execute(alipayTradeFastpayRefundQueryRequest);
            payTradeRefund4Alipay.callRefundQuerySucc(alipayTradeFastpayRefundQueryResponse);
        } catch (AlipayApiException e) {
            log.error("调用支付宝退款查询单号:[{}]对应的退款异常信息:[{}]", payTradeRefund4Alipay.getId(), getFullStackTrace(e));
            payTradeRefund4Alipay.callRefundQueryFail("支付宝交易查询异常!");
        }

        payTradeRefundMapper.updateByAlipayRefundQuery(payTradeRefund4Alipay);
    }

    private AlipayTradeFastpayRefundQueryRequest getAlipayTradeFastpayRefundQueryRequest(PayLog payLog, PayTradeRefund4Alipay payTradeRefund4Alipay) {
        AlipayTradeFastpayRefundQueryRequest alipayTradeFastpayRefundQueryRequest = new AlipayTradeFastpayRefundQueryRequest();
        AlipayTradeFastpayRefundQueryModel alipayTradeFastpayRefundQueryModel = getAlipayTradeFastpayRefundQueryModel(payLog, payTradeRefund4Alipay);
        alipayTradeFastpayRefundQueryRequest.setBizModel(alipayTradeFastpayRefundQueryModel);
        return alipayTradeFastpayRefundQueryRequest;
    }

    private AlipayTradeFastpayRefundQueryModel getAlipayTradeFastpayRefundQueryModel(PayLog payLog, PayTradeRefund4Alipay payTradeRefund4Alipay) {
        AlipayTradeFastpayRefundQueryModel alipayTradeFastpayRefundQueryModel = new AlipayTradeFastpayRefundQueryModel();
        alipayTradeFastpayRefundQueryModel.setTradeNo(payLog.getThirdSerialNo());
        alipayTradeFastpayRefundQueryModel.setOutTradeNo(payLog.getAppSheetSerialNo());
        alipayTradeFastpayRefundQueryModel.setOutRequestNo(payTradeRefund4Alipay.getRefundNo());
        return alipayTradeFastpayRefundQueryModel;
    }
}
