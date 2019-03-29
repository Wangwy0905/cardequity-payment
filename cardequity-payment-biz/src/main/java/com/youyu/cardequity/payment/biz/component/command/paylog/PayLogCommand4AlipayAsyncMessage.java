package com.youyu.cardequity.payment.biz.component.command.paylog;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.component.properties.AlipayProperties;
import com.youyu.cardequity.payment.biz.dal.dao.PayLogMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog4Alipay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.youyu.cardequity.payment.biz.enums.RouteVoIdFlagEnum.FAIL;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Alipay Pay异步支付命令
 */
@Slf4j
@StatusAndStrategyNum(superClass = PayLogCommand.class, number = "2", describe = "支付宝异步接收参数")
@Component
public class PayLogCommand4AlipayAsyncMessage extends PayLogCommand {

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private PayLogMapper payLogMapper;

    /**
     * 退款状态包含：
     * 1、REFUND_SUCCESS：退款成功，分两种情况
     * 全额退款情况：trade_status= TRADE_CLOSED，而refund_status=REFUND_SUCCESS
     * 非全额退款情况：trade_status= TRADE_SUCCESS，而refund_status=REFUND_SUCCESS
     * 2、REFUND_CLOSED：退款关闭
     * <p>
     * <p>
     * 不返回success，异步通知发送几次
     * 接收到异步通知后，未返回success，异步通知发送几次？
     * 异步通知分为四个状态：
     * WAIT_BUYER_PAY 交易创建，等待买家付款
     * TRADE_CLOSED 未付款交易超时关闭，或支付完成后全额退款
     * TRADE_SUCCESS 交易支付成功
     * TRADE_FINISHED 交易结束，不可退款
     * 1、不同的接口会返回不同状态的异步通知，具体返回以接口文档为准，
     * 2、每种状态通知接收之后都必须返回success这七个字符，不带任何其他字符包括空格，返回了success则不会再发送通知，
     * 3、不返回success，会在25小时以内完成8次通知（通知的间隔频率一般是：4m,10m,10m,1h,2h,6h,15h）；才会结束通知发送。
     *
     * @param payLog
     * @param t      支付宝异步参数:Map
     * @param <T>
     * @return
     */
    @Override
    public <T> void executeCmd(PayLog payLog, T t) {
        Map<String, String> params2Map = (Map<String, String>) t;
        PayLog4Alipay payLog4Alipay = (PayLog4Alipay) payLog;
        payLog4Alipay.analysisAlipayAsyncMessage(params2Map, alipayProperties.getSellerId(), alipayProperties.getAppId(), alipayProperties.getAlipayPublicKey());
        payLogMapper.updateAlipayAsyncMessage(payLog4Alipay);

        if (payLog4Alipay.isPaySucc()) {
            payLogMapper.updateAppSheetSerialNo4OtherPayIdRouteVoIdFlag(payLog4Alipay.getAppSheetSerialNo(), payLog4Alipay.getId(), FAIL.getCode());
        }
    }

}
