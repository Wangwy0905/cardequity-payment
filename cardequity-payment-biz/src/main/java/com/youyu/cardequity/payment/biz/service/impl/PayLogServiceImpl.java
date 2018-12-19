package com.youyu.cardequity.payment.biz.service.impl;

import com.youyu.cardequity.payment.biz.component.properties.AlipayProperties;
import com.youyu.cardequity.payment.biz.dal.dao.PayChannelInfoMapper;
import com.youyu.cardequity.payment.biz.dal.dao.PayLogMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayChannelInfo;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog4Alipay;
import com.youyu.cardequity.payment.biz.service.PayLogService;
import com.youyu.cardequity.payment.dto.PayLogDto;
import com.youyu.cardequity.payment.dto.PayLogResponseDto;
import com.youyu.cardequity.payment.dto.TradeCloseDto;
import com.youyu.cardequity.payment.dto.TradeCloseResponseDto;
import com.youyu.cardequity.payment.dto.alipay.AlipaySyncMessageDto;
import com.youyu.cardequity.payment.dto.alipay.AlipaySyncMessageResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson.JSON.toJSONString;
import static com.youyu.cardequity.common.base.converter.BeanPropertiesConverter.copyProperties;
import static com.youyu.cardequity.payment.biz.enums.RouteVoIdFlagEnum.NORMAL;
import static com.youyu.cardequity.payment.biz.help.constant.Constant.ALIPAY_ASYNC_RESPONSE_FAIL;
import static com.youyu.cardequity.payment.biz.help.constant.Constant.ALIPAY_OUT_TRADE_NO;
import static java.util.Objects.isNull;
import static org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace;
import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work PayLog支付日志Service impl
 */
@Slf4j
@Service
public class PayLogServiceImpl implements PayLogService {

    @Autowired
    private PayChannelInfoMapper payChannelInfoMapper;
    @Autowired
    private PayLogMapper payLogMapper;

    @Autowired
    private AlipayProperties alipayProperties;

    @Override
    @Transactional
    public PayLogResponseDto pay(PayLogDto payLogDto) {
        String payChannelNo = payLogDto.getPayChannelNo();
        PayChannelInfo payChannelInfo = payChannelInfoMapper.getById(payChannelNo);
        PayLog payLog = payChannelInfo.createPayLogAndPay(payLogDto);

        return copyProperties(payLog, PayLogResponseDto.class);
    }

    @Override
    @Transactional
    public void alipaySyncMessage(AlipaySyncMessageDto alipaySyncMessageDto) {
        log.info("支付宝同步通知参数信息如下:[{}]", toJSONString(alipaySyncMessageDto));
        AlipaySyncMessageResultDto alipaySyncMessageResultDto = alipaySyncMessageDto.getAlipaySyncMessageResultDto();
        if (isNull(alipaySyncMessageResultDto)) {
            return;
        }

        String alipayOutTradeNo = getAlipayOutTradeNo(alipaySyncMessageDto);
        PayLog4Alipay payLog4Alipay = (PayLog4Alipay) payLogMapper.getByAppSheetSerialNoRouteVoIdFlag(alipayOutTradeNo, NORMAL.getCode());
        payLog4Alipay.alipaySyncMessage(alipaySyncMessageDto);
    }

    @Override
    public String alipayAsyncMessage(Map<String, String> params2Map) {
        if (isEmpty(params2Map)) {
            return ALIPAY_ASYNC_RESPONSE_FAIL;
        }

        String outTradeNo = params2Map.get(ALIPAY_OUT_TRADE_NO);
        PayLog4Alipay payLog4Alipay = (PayLog4Alipay) payLogMapper.getByAppSheetSerialNoRouteVoIdFlag(outTradeNo, NORMAL.getCode());
        if (isNull(payLog4Alipay)) {
            return ALIPAY_ASYNC_RESPONSE_FAIL;
        }

        String ourResponse2Alipay = payLog4Alipay.alipayAsyncMessage(params2Map);
        return ourResponse2Alipay;
    }

    @Override
    public TradeCloseResponseDto tradeClose(TradeCloseDto tradeCloseDto) {
        String appSheetSerialNo = tradeCloseDto.getAppSheetSerialNo();
        PayLog payLog = payLogMapper.getByAppSheetSerialNoRouteVoIdFlag(appSheetSerialNo, NORMAL.getCode());

        payLog.tradeClose(tradeCloseDto);
        return copyProperties(payLog, TradeCloseResponseDto.class);
    }

    @Override
    public void timeTradeQuery() {
        List<PayLog> payLogs = payLogMapper.getByTimeTradeQuery(alipayProperties.getAsyncNotifyThresholdStart(), alipayProperties.getAsyncNotifyThresholdEnd());
        for (PayLog payLog : payLogs) {
            payLog.tradeQuery();
        }
    }

    /**
     * 获取订单单号
     *
     * @param alipaySyncMessageDto
     * @return
     */
    private String getAlipayOutTradeNo(AlipaySyncMessageDto alipaySyncMessageDto) {
        try {
            String outTradeNo = alipaySyncMessageDto.getAlipaySyncMessageResultDto().getAlipayTradeAppPayResponse().getOutTradeNo();
            return outTradeNo;
        } catch (Exception ex) {
            log.error("获取支付宝订单异常信息:[{}]", getFullStackTrace(ex));
            return null;
        }
    }

}
