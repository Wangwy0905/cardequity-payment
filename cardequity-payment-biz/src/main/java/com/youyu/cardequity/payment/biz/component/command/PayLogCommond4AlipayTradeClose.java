package com.youyu.cardequity.payment.biz.component.command;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog4Alipay;
import com.youyu.cardequity.payment.dto.alipay.AlipayTradeCloseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.youyu.cardequity.payment.enums.PaymentResultCodeEnum.ALIPAY_TRANSACTIONS_CLOSED_FAIL;
import static com.youyu.cardequity.payment.enums.PaymentResultCodeEnum.ALIPAY_TRANSACTION_CLOSED_EXCEPTION;
import static com.youyu.common.api.Result.fail;
import static com.youyu.common.api.Result.ok;
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

    @Value("${alipay.sellerId:}")
    private String sellerId;
    @Value("${alipay.appId:}")
    private String appId;
    @Value("${alipay.alipayPublicKey:}")
    private String alipayPublicKey;

    @Autowired
    private AlipayClient alipayClient;

    @Override
    public <T, R> R executeCmd(PayLog payLog, T t) {
        AlipayTradeCloseDto alipayTradeCloseDto = (AlipayTradeCloseDto) t;
        PayLog4Alipay payLog4Alipay = (PayLog4Alipay) payLog;
        AlipayTradeCloseRequest alipayTradeCloseRequest = getAlipayTradeCloseRequest(payLog4Alipay, alipayTradeCloseDto);
        try {
            AlipayTradeCloseResponse alipayTradeCloseResponse = alipayClient.execute(alipayTradeCloseRequest);
            payLog4Alipay.analysisAlipayTradeClose(alipayTradeCloseResponse);

            boolean flag = alipayTradeCloseResponse.isSuccess();
            return (R) (flag ? ok() : fail(ALIPAY_TRANSACTIONS_CLOSED_FAIL.getCode(), ALIPAY_TRANSACTIONS_CLOSED_FAIL.getFormatDesc(payLog.getAppSheetSerialNo())));
        } catch (AlipayApiException e) {
            log.error("调用支付宝关闭订单单号:[{}]对应的交易异常信息:[{}]", payLog.getAppSheetSerialNo(), getFullStackTrace(e));
            return (R) fail(ALIPAY_TRANSACTION_CLOSED_EXCEPTION.getCode(), ALIPAY_TRANSACTION_CLOSED_EXCEPTION.getFormatDesc(payLog.getAppSheetSerialNo()));
        }
    }

    private AlipayTradeCloseRequest getAlipayTradeCloseRequest(PayLog4Alipay payLog4Alipay, AlipayTradeCloseDto alipayTradeCloseDto) {
        AlipayTradeCloseRequest alipayTradeCloseRequest = new AlipayTradeCloseRequest();
        AlipayTradeCloseModel alipayTradeCloseModel = getAlipayTradeCloseModel(payLog4Alipay, alipayTradeCloseDto);
        alipayTradeCloseRequest.setBizModel(alipayTradeCloseModel);
        return alipayTradeCloseRequest;
    }

    private AlipayTradeCloseModel getAlipayTradeCloseModel(PayLog4Alipay payLog4Alipay, AlipayTradeCloseDto alipayTradeCloseDto) {
        AlipayTradeCloseModel alipayTradeCloseModel = new AlipayTradeCloseModel();
        alipayTradeCloseModel.setOperatorId(alipayTradeCloseDto.getOperatorId());
        alipayTradeCloseModel.setOutTradeNo(alipayTradeCloseDto.getAppSheetSerialNo());
        alipayTradeCloseModel.setTradeNo(payLog4Alipay.getThirdSerialNo());
        return alipayTradeCloseModel;
    }

}
