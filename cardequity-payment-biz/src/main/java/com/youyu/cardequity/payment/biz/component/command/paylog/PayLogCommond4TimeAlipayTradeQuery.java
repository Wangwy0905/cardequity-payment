package com.youyu.cardequity.payment.biz.component.command.paylog;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.component.rabbitmq.RabbitmqSender;
import com.youyu.cardequity.payment.biz.dal.dao.PayLogMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog4Alipay;
import com.youyu.cardequity.payment.dto.PayLogAsyncMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.alibaba.fastjson.JSON.toJSONString;
import static com.youyu.cardequity.common.base.converter.BeanPropertiesConverter.copyProperties;
import static com.youyu.cardequity.payment.biz.enums.RabbitmqMessageEnum.PAY_ASYNC_MESSAGE;
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

    @Autowired
    private RabbitmqSender rabbitmqSender;

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
            payLog4Alipay.analysisAlipayTradeQueryResponse(alipayTradeQueryResponse);
            payLogMapper.updateAlipayTradeQuery(payLog);

            PayLogAsyncMessageDto payLogAsyncMessageDto = copyProperties(payLog4Alipay, PayLogAsyncMessageDto.class);
            String message = toJSONString(payLogAsyncMessageDto);
            log.info("定时任务主动查询支付宝支付未收到异步通知的支付结果通知交易系统支付流水号:[{}]和消息信息:[{}]", payLog4Alipay.getId(), message);
            rabbitmqSender.sendMessage(message, PAY_ASYNC_MESSAGE);
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
