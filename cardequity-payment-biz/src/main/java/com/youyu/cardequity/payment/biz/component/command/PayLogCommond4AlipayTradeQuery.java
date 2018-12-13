package com.youyu.cardequity.payment.biz.component.command;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog4Alipay;
import com.youyu.cardequity.payment.dto.alipay.AlipayTradeQueryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Alipay Pay交易查询命令
 */
@Slf4j
@StatusAndStrategyNum(superClass = PayLogCommond.class, number = "4", describe = "支付宝交易查询")
@Component
public class PayLogCommond4AlipayTradeQuery extends PayLogCommond {

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
        PayLog4Alipay payLog4Alipay = (PayLog4Alipay) payLog;
        AlipayTradeQueryDto alipayTradeQueryDto = (AlipayTradeQueryDto) t;

        AlipayTradeQueryRequest alipayTradeQueryRequest = getAlipayTradeQueryRequest(payLog4Alipay, alipayTradeQueryDto);
        try {
            AlipayTradeQueryResponse alipayTradeQueryResponse = alipayClient.execute(alipayTradeQueryRequest);
            if (alipayTradeQueryResponse.isSuccess()) {
                System.out.println("调用成功");
            } else {
                System.out.println("调用失败");
            }
            return null;
        } catch (AlipayApiException e) {
            return null;
        }
    }

    private AlipayTradeQueryRequest getAlipayTradeQueryRequest(PayLog4Alipay payLog4Alipay, AlipayTradeQueryDto alipayTradeQueryDto) {
        AlipayTradeQueryRequest alipayTradeQueryRequest = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel alipayTradeQueryModel = getAlipayTradeQueryModel(payLog4Alipay, alipayTradeQueryDto);
        alipayTradeQueryRequest.setBizModel(alipayTradeQueryModel);
        return alipayTradeQueryRequest;
    }

    private AlipayTradeQueryModel getAlipayTradeQueryModel(PayLog4Alipay payLog4Alipay, AlipayTradeQueryDto alipayTradeQueryDto) {
        AlipayTradeQueryModel alipayTradeQueryModel = new AlipayTradeQueryModel();
        alipayTradeQueryModel.setOutTradeNo(alipayTradeQueryDto.getAppSheetSerialNo());
        alipayTradeQueryModel.setTradeNo(payLog4Alipay.getThirdSerialNo());
        return null;
    }

}
