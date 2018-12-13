package com.youyu.cardequity.payment.biz.service.impl;

import com.youyu.cardequity.payment.biz.component.command.PayLogCommond4AlipayAsyncMessage;
import com.youyu.cardequity.payment.biz.component.command.PayLogCommond4AlipaySyncMessage;
import com.youyu.cardequity.payment.biz.component.command.PayLogCommond4AlipayTradeClose;
import com.youyu.cardequity.payment.biz.component.command.PayLogCommond4TimeAlipayTradeQuery;
import com.youyu.cardequity.payment.biz.dal.dao.PayChannelInfoMapper;
import com.youyu.cardequity.payment.biz.dal.dao.PayLogMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayChannelInfo;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.service.PayLogService;
import com.youyu.cardequity.payment.dto.AlipayPrepayment4PayLogDto;
import com.youyu.cardequity.payment.dto.PayLogDto;
import com.youyu.cardequity.payment.dto.alipay.AlipaySyncMessageDto;
import com.youyu.cardequity.payment.dto.alipay.AlipaySyncMessageResultDto;
import com.youyu.cardequity.payment.dto.alipay.AlipayTradeCloseDto;
import com.youyu.common.api.Result;
import com.youyu.common.dto.BaseDto;
import com.youyu.common.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson.JSON.toJSONString;
import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;
import static com.youyu.cardequity.payment.biz.help.constant.Constant.*;
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
public class PayLogServiceImpl extends AbstractService<String, PayLogDto, PayLog, PayLogMapper> implements PayLogService {

    @Autowired
    private PayChannelInfoMapper payChannelInfoMapper;
    @Autowired
    private PayLogMapper payLogMapper;

    @Value("${alipay.async.notify.threshold.start:15}")
    private Integer alipayAsyncNotifyThresholdStart;
    @Value("${alipay.async.notify.threshold.end:10}")
    private Integer alipayAsyncNotifyThresholdEnd;

    @Override
    public <T extends BaseDto> T alipay(PayLogDto payLogDto, PayLogService payLogService) {
        // TODO: 2018/12/13 支持重复做
        String payChannelNo = payLogDto.getPayChannelNo();
        PayChannelInfo payChannelInfo = payChannelInfoMapper.getById(payChannelNo);
        PayLog payLog = payChannelInfo.createPayLog(payLogDto);
        payLogService.save(payLog);

        return payLogService.doPay(payLog.getId());
    }

    @Override
    @Transactional
    public void save(PayLog payLog) {
        payLogMapper.insertSelective(payLog);
    }

    @Override
    @Transactional
    public <T extends BaseDto> T doPay(String id) {
        PayLog payLog = payLogMapper.getById(id);

        AlipayPrepayment4PayLogDto alipayPrepayment4PayLogDto = payLog.pay();
        payLogMapper.updateAlipayPrepayment(payLog);
        return (T) alipayPrepayment4PayLogDto;
    }

    @Override
    public void alipaySyncMessage(AlipaySyncMessageDto alipaySyncMessageDto) {
        log.info("支付宝同步通知参数信息如下:[{}]", toJSONString(alipaySyncMessageDto));
        AlipaySyncMessageResultDto alipaySyncMessageResultDto = alipaySyncMessageDto.getAlipaySyncMessageResultDto();
        if (isNull(alipaySyncMessageResultDto)) {
            return;
        }

        String alipayOutTradeNo = getAlipayOutTradeNo(alipaySyncMessageDto);
        PayLog payLog = payLogMapper.getByAppSheetSerialNo(alipayOutTradeNo);
        payLog.doCommand(getBeanByClass(PayLogCommond4AlipaySyncMessage.class), alipaySyncMessageDto);
        payLogMapper.updateAlipaySyncMessage(payLog);
    }

    @Override
    public String alipayAsyncMessage(Map<String, String> params2Map) {
        if (isEmpty(params2Map)) {
            return ALIPAY_ASYNC_RESPONSE_FAIL;
        }

        String outTradeNo = params2Map.get(ALIPAY_OUT_TRADE_NO);
        PayLog payLog = payLogMapper.getByAppSheetSerialNo(outTradeNo);
        if (isNull(payLog)) {
            return ALIPAY_ASYNC_RESPONSE_FAIL;
        }

        String ourResponse2Alipay = payLog.doCommand(getBeanByClass(PayLogCommond4AlipayAsyncMessage.class), params2Map);
        payLogMapper.updateAlipayAsyncMessage(payLog);
        return ourResponse2Alipay;
    }

    @Override
    public Result alipayTradeClose(AlipayTradeCloseDto alipayTradeCloseDto) {
        String appSheetSerialNo = alipayTradeCloseDto.getAppSheetSerialNo();
        PayLog payLog = payLogMapper.getByAppSheetSerialNo(appSheetSerialNo);
        Result result = payLog.doCommand(getBeanByClass(PayLogCommond4AlipayTradeClose.class), alipayTradeCloseDto);
        payLogMapper.updateAlipayTradeClose(payLog);
        return result;
    }

    @Override
    public void timeAlipayTradeQuery() {
        List<PayLog> payLogs = payLogMapper.getByTimeAlipayTradeQuery(alipayAsyncNotifyThresholdStart, alipayAsyncNotifyThresholdEnd, ALIPAY_PAY_TYPE, ALIPAY_ASYNC_RESPONSE_SUCC);
        for (PayLog payLog : payLogs) {
            payLog.doCommand(getBeanByClass(PayLogCommond4TimeAlipayTradeQuery.class), null);
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
