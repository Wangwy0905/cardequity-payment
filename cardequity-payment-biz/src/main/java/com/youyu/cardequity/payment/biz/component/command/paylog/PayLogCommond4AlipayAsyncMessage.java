package com.youyu.cardequity.payment.biz.component.command.paylog;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.dal.dao.PayLogMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog4Alipay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Alipay Pay异步支付命令
 */
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
        if (payLog4Alipay.canSendMsg2Trade()) {
            // TODO: 2018/12/11 发送消息到交易系统,通知回调:把payLog4Alipay的支付状态发过去
        }
    }

}
