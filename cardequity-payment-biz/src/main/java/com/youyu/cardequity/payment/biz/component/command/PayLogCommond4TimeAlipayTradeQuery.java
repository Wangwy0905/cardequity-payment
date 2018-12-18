package com.youyu.cardequity.payment.biz.component.command;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.dal.dao.PayLogMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog4Alipay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Alipay 定时Pay交易查询命令:查询阈值内未收到支付异步通知
 */
@Slf4j
@StatusAndStrategyNum(superClass = PayLogCommond.class, number = "4", describe = "定时支付宝交易查询")
@Component
public class PayLogCommond4TimeAlipayTradeQuery extends PayLogCommond {

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private PayLogMapper payLogMapper;

    /**
     * @param payLog
     * @param t      定时调用t:null
     * @param <T>
     * @return
     */
    @Override
    public <T> void executeCmd(PayLog payLog, T t) {
        PayLog4Alipay payLog4Alipay = (PayLog4Alipay) payLog;
        AlipayTradeQueryRequest alipayTradeQueryRequest = getAlipayTradeQueryRequest(payLog4Alipay);
        try {
            AlipayTradeQueryResponse alipayTradeQueryResponse = alipayClient.execute(alipayTradeQueryRequest);
            boolean tradeQueryFlag = payLog4Alipay.analysisAlipayTradeQueryResponse(alipayTradeQueryResponse);
            if (tradeQueryFlag) {
                payLogMapper.updateAlipayTradeQuery(payLog);
                // TODO: 2018/12/11 发送消息到交易系统,通知回调:把payLog4Alipay的支付状态发过去
            }
        } catch (AlipayApiException e) {
            log.error("调用支付宝交易查询订单:[{}]对应的交易异常信息:[{}]", payLog.getAppSheetSerialNo(), getFullStackTrace(e));
        }
    }

    private AlipayTradeQueryRequest getAlipayTradeQueryRequest(PayLog4Alipay payLog4Alipay) {
        AlipayTradeQueryRequest alipayTradeQueryRequest = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel alipayTradeQueryModel = getAlipayTradeQueryModel(payLog4Alipay);
        alipayTradeQueryRequest.setBizModel(alipayTradeQueryModel);
        return alipayTradeQueryRequest;
    }

    private AlipayTradeQueryModel getAlipayTradeQueryModel(PayLog4Alipay payLog4Alipay) {
        AlipayTradeQueryModel alipayTradeQueryModel = new AlipayTradeQueryModel();
        alipayTradeQueryModel.setOutTradeNo(payLog4Alipay.getAppSheetSerialNo());
        alipayTradeQueryModel.setTradeNo(payLog4Alipay.getThirdSerialNo());
        return alipayTradeQueryModel;
    }

}
