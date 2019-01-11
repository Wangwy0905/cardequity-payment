package com.youyu.cardequity.payment.biz.service.impl;

import com.youyu.cardequity.payment.biz.dal.dao.*;
import com.youyu.cardequity.payment.biz.dal.entity.*;
import com.youyu.cardequity.payment.biz.service.PayCheckDeatailService;
import com.youyu.cardequity.payment.dto.PayCheckDeatailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.alibaba.fastjson.JSON.toJSONString;
import static com.youyu.cardequity.common.base.util.DateUtil.*;
import static com.youyu.cardequity.payment.biz.enums.CheckStatusEnum.MAY_BE_REFUND_UNILATERAL;
import static com.youyu.cardequity.payment.biz.enums.CheckStatusEnum.MAY_BE_TRADE_UNILATERAL;
import static com.youyu.cardequity.payment.biz.enums.RouteVoIdFlagEnum.NORMAL;
import static java.util.Objects.isNull;
import static org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.time.DateUtils.addDays;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 对账信息管理 Service impl
 */
@Slf4j
@Service
public class PayCheckDeatailServiceImpl implements PayCheckDeatailService {

    @Autowired
    private TradeOrderMapper tradeOrderMapper;
    @Autowired
    private PayCheckFileDeatailMapper payCheckFileDeatailMapper;
    @Autowired
    private PayChannelInfoMapper payChannelInfoMapper;
    @Autowired
    private PayCheckDeatailMapper payCheckDeatailMapper;
    @Autowired
    private PayLogMapper payLogMapper;
    @Autowired
    private PayTradeRefundMapper payTradeRefundMapper;

    @Override
    public void reconciliation(PayCheckDeatailDto payCheckDeatailDto, PayCheckDeatailService payCheckDeatailService) {
        protectPayCheckDeatailDto(payCheckDeatailDto);

        bill2TradeAndRefund(payCheckDeatailDto, payCheckDeatailService);

        tradeAndRefund2Bill(payCheckDeatailDto, payCheckDeatailService);
    }

    private void bill2TradeAndRefund(PayCheckDeatailDto payCheckDeatailDto, PayCheckDeatailService payCheckDeatailService) {
        List<PayCheckFileDeatail> payCheckFileDeatails = payCheckFileDeatailMapper.getListByBillDatePayCheckDeatailIdisNull(payCheckDeatailDto.getBillDate());
        for (PayCheckFileDeatail payCheckFileDeatail : payCheckFileDeatails) {
            payCheckDeatailService.executeBill2TradeAndRefund(payCheckFileDeatail);
        }
    }

    @Override
    @Transactional
    public void executeBill2TradeAndRefund(PayCheckFileDeatail payCheckFileDeatail) {
        String refundBatchNo = payCheckFileDeatail.getRefundBatchNo();
        try {
            if (isBlank(refundBatchNo)) {
                doBill2Trade(payCheckFileDeatail);
                return;
            }
            doBill2TradeRefund(payCheckFileDeatail);
        } catch (Exception ex) {
            log.error("文件对交易对账单信息:[{}]和异常信息:[{}]", toJSONString(payCheckFileDeatail), getFullStackTrace(ex));
        }
    }

    private void doBill2Trade(PayCheckFileDeatail payCheckFileDeatail) {
        TradeOrder tradeOrder = tradeOrderMapper.getByAppSheetSerialNoPayRefundNoIsNull(payCheckFileDeatail.getAppSheetSerialNo());
        if (isNull(tradeOrder)) {
            // 文件单边交易
            PayLog payLog = payLogMapper.getByAppSheetSerialNoRouteVoIdFlag(payCheckFileDeatail.getAppSheetSerialNo(), NORMAL.getCode());
            PayCheckDeatail payCheckDeatail = new PayCheckDeatail(payCheckFileDeatail, payLog);
            payCheckDeatailMapper.insertSelective(payCheckDeatail);
            return;
        }

        PayChannelInfo payChannelInfo = payChannelInfoMapper.getById(tradeOrder.getPayChannelNo());
        payChannelInfo.doBill2Trade(payCheckFileDeatail, tradeOrder);
    }

    private void doBill2TradeRefund(PayCheckFileDeatail payCheckFileDeatail) {
        TradeOrder tradeOrder = tradeOrderMapper.getByAppSeetSerialNoPayRefundNo(payCheckFileDeatail.getAppSheetSerialNo(), payCheckFileDeatail.getRefundBatchNo());
        if (isNull(tradeOrder)) {
            // 文件单边退款
            PayTradeRefund payTradeRefund = payTradeRefundMapper.getByAppSheetSerialNoRefundNo(payCheckFileDeatail.getAppSheetSerialNo(), payCheckFileDeatail.getRefundBatchNo());
            PayCheckDeatail payCheckDeatail = new PayCheckDeatail(payCheckFileDeatail, payTradeRefund);
            payCheckDeatailMapper.insertSelective(payCheckDeatail);
            return;
        }

        PayChannelInfo payChannelInfo = payChannelInfoMapper.getById(tradeOrder.getPayChannelNo());
        payChannelInfo.doBill2TradeRefund(payCheckFileDeatail, tradeOrder);
    }

    private void tradeAndRefund2Bill(PayCheckDeatailDto payCheckDeatailDto, PayCheckDeatailService payCheckDeatailService) {
        List<TradeOrder> tradeOrders = tradeOrderMapper.getBySyncDataDateAndDayCut(getSyncDataDate(payCheckDeatailDto.getBillDate()), MAY_BE_TRADE_UNILATERAL.getCode(), MAY_BE_REFUND_UNILATERAL.getCode());
        for (TradeOrder tradeOrder : tradeOrders) {
            payCheckDeatailService.executeTradeAndRefund2Bill(tradeOrder);
        }
    }

    @Override
    @Transactional
    public void executeTradeAndRefund2Bill(TradeOrder tradeOrder) {
        String payRefundNo = tradeOrder.getPayRefundNo();
        try {
            if (isBlank(payRefundNo)) {
                doTrade2Bill(tradeOrder);
                return;
            }

            doTrade2BillRefund(tradeOrder);
        } catch (Exception ex) {
            log.error("交易对文件交易信息:[{}]和异常信息:[{}]", toJSONString(tradeOrder), getFullStackTrace(ex));
        }
    }

    private void doTrade2BillRefund(TradeOrder tradeOrder) {
        PayCheckFileDeatail payCheckFileDeatail = payCheckFileDeatailMapper.getByAppSeetSerialNoRefundBatchNo(tradeOrder.getAppSheetSerialNo(), tradeOrder.getPayRefundNo());
        if (isNull(payCheckFileDeatail)) {
            PayChannelInfo payChannelInfo = payChannelInfoMapper.getById(tradeOrder.getPayChannelNo());
            payChannelInfo.doTrade2BillRefundNotFile(tradeOrder);
            return;
        }

        PayChannelInfo payChannelInfo = payChannelInfoMapper.getById(tradeOrder.getPayChannelNo());
        payChannelInfo.doTrade2BillRefund(tradeOrder, payCheckFileDeatail);
    }

    private void doTrade2Bill(TradeOrder tradeOrder) {
        PayCheckFileDeatail payCheckFileDeatail = payCheckFileDeatailMapper.getByAppSeetSerialNoRefundBatchNoIsNull(tradeOrder.getAppSheetSerialNo());
        if (isNull(payCheckFileDeatail)) {
            PayChannelInfo payChannelInfo = payChannelInfoMapper.getById(tradeOrder.getPayChannelNo());
            payChannelInfo.doTrade2BillNotFile(tradeOrder);
            return;
        }

        PayChannelInfo payChannelInfo = payChannelInfoMapper.getById(tradeOrder.getPayChannelNo());
        payChannelInfo.doTrade2Bill(tradeOrder, payCheckFileDeatail);
    }

    private void protectPayCheckDeatailDto(PayCheckDeatailDto payCheckDeatailDto) {
        String billDate = payCheckDeatailDto.getBillDate();
        if (isBlank(billDate)) {
            billDate = date2String(addDays(now(), -1), YYYYMMDD);
        }
        payCheckDeatailDto.setBillDate(billDate);
    }

    private String getSyncDataDate(String billDate) {
        Date date = addDays(string2Date(billDate, YYYYMMDD), 1);
        return date2String(date, YYYYMMDD);
    }
}
