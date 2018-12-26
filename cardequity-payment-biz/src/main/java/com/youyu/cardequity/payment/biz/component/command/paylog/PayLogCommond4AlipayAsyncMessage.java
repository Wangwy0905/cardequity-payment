package com.youyu.cardequity.payment.biz.component.command.paylog;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.component.rabbitmq.RabbitmqSender;
import com.youyu.cardequity.payment.biz.dal.dao.PayLogMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog4Alipay;
import com.youyu.cardequity.payment.dto.PayLogAsyncMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.alibaba.fastjson.JSON.toJSONString;
import static com.youyu.cardequity.common.base.converter.BeanPropertiesConverter.copyProperties;
import static com.youyu.cardequity.payment.biz.enums.RabbitmqMessageEnum.ALIPAY_ASYNC_MESSAGE;
import static com.youyu.cardequity.payment.biz.enums.RouteVoIdFlagEnum.FAIL;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Alipay Pay异步支付命令
 */
@Slf4j
@StatusAndStrategyNum(superClass = PayLogCommond.class, number = "2", describe = "支付宝异步接收参数")
@Component
public class PayLogCommond4AlipayAsyncMessage extends PayLogCommond {

    @Value("${alipay.sellerId:}")
    private String sellerId;
    @Value("${alipay.appId:}")
    private String appId;
    @Value("${alipay.alipayPublicKey:}")
    private String alipayPublicKey;

    @Autowired
    private PayLogMapper payLogMapper;

    @Autowired
    private RabbitmqSender rabbitmqSender;

    /**
     * @param payLog
     * @param t      支付宝异步参数:Map
     * @param <T>
     * @return
     */
    @Override
    public <T> void executeCmd(PayLog payLog, T t) {
        Map<String, String> params2Map = (Map<String, String>) t;
        PayLog4Alipay payLog4Alipay = (PayLog4Alipay) payLog;
        payLog4Alipay.analysisAlipayAsyncMessage(params2Map, sellerId, appId, alipayPublicKey);
        payLogMapper.updateAlipayAsyncMessage(payLog4Alipay);

        if (payLog4Alipay.isPaySucc()) {
            payLogMapper.updateAppSheetSerialNo4OtherPayIdRouteVoIdFlag(payLog4Alipay.getAppSheetSerialNo(), payLog4Alipay.getId(), FAIL.getCode());
        }

        if (payLog4Alipay.canSendMsg2Trade()) {
            PayLogAsyncMessageDto payLogAsyncMessageDto = copyProperties(payLog4Alipay, PayLogAsyncMessageDto.class);
            String message = toJSONString(payLogAsyncMessageDto);
            log.info("异步通知交易系统支付宝支付对应的支付流水号:[{}]和消息信息:[{}]", payLog4Alipay.getId(), message);
            rabbitmqSender.sendMessage(message, ALIPAY_ASYNC_MESSAGE);
        }
    }

}
