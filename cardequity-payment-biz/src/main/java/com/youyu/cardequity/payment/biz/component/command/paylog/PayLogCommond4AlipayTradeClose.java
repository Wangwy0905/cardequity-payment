package com.youyu.cardequity.payment.biz.component.command.paylog;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.dal.dao.PayLogMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog4Alipay;
import com.youyu.cardequity.payment.dto.TradeCloseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Alipay Pay交易关闭命令
 */
@Slf4j
@StatusAndStrategyNum(superClass = PayLogCommond.class, number = "3", describe = "支付宝交易关闭")
@Component
public class PayLogCommond4AlipayTradeClose extends PayLogCommond {

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private PayLogMapper payLogMapper;

    /**
     * @param payLog
     * @param t      支付宝交易关闭参数:AlipayTradeCloseDto
     * @param <T>
     * @return
     */
    @Override
    public <T> void executeCmd(PayLog payLog, T t) {
        TradeCloseDto tradeCloseDto = (TradeCloseDto) t;
        PayLog4Alipay payLog4Alipay = (PayLog4Alipay) payLog;

        AlipayTradeCloseRequest alipayTradeCloseRequest = getAlipayTradeCloseRequest(payLog4Alipay, tradeCloseDto);
        try {
            AlipayTradeCloseResponse alipayTradeCloseResponse = alipayClient.execute(alipayTradeCloseRequest);
            payLog4Alipay.callAlipayTradeCloseSucc(alipayTradeCloseResponse);
        } catch (AlipayApiException e) {
            log.error("调用支付宝关闭订单单号:[{}]对应的交易异常信息:[{}]", payLog.getAppSheetSerialNo(), getFullStackTrace(e));
            payLog4Alipay.callAlipayTradeCloseFail("调用支付宝交易关闭异常!");
        }
        payLogMapper.updateAlipayTradeClose(payLog);
    }

    private AlipayTradeCloseRequest getAlipayTradeCloseRequest(PayLog4Alipay payLog4Alipay, TradeCloseDto tradeCloseDto) {
        AlipayTradeCloseRequest alipayTradeCloseRequest = new AlipayTradeCloseRequest();
        AlipayTradeCloseModel alipayTradeCloseModel = getAlipayTradeCloseModel(payLog4Alipay, tradeCloseDto);
        alipayTradeCloseRequest.setBizModel(alipayTradeCloseModel);
        return alipayTradeCloseRequest;
    }

    private AlipayTradeCloseModel getAlipayTradeCloseModel(PayLog4Alipay payLog4Alipay, TradeCloseDto tradeCloseDto) {
        AlipayTradeCloseModel alipayTradeCloseModel = new AlipayTradeCloseModel();
        alipayTradeCloseModel.setOperatorId(tradeCloseDto.getOperatorId());
        alipayTradeCloseModel.setOutTradeNo(tradeCloseDto.getAppSheetSerialNo());
        alipayTradeCloseModel.setTradeNo(payLog4Alipay.getThirdSerialNo());
        return alipayTradeCloseModel;
    }

}
