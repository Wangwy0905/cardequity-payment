package com.youyu.cardequity.payment.biz.service.impl;

import com.youyu.cardequity.common.base.util.UuidUtil;
import com.youyu.cardequity.common.spring.service.BatchService;
import com.youyu.cardequity.payment.biz.component.status.paylog.PayLogStatus;
import com.youyu.cardequity.payment.biz.component.status.paylog.PayLogStatus4PaymentQueuing;
import com.youyu.cardequity.payment.biz.component.status.paytraderefund.PayTradeRefundStatus;
import com.youyu.cardequity.payment.biz.dal.dao.PayCheckFileDeatailMapper;
import com.youyu.cardequity.payment.biz.dal.dao.PayLogMapper;
import com.youyu.cardequity.payment.biz.dal.dao.PayTradeRefundMapper;
import com.youyu.cardequity.payment.biz.dal.dao.TradeOrderMapper;
import com.youyu.cardequity.payment.biz.dal.entity.*;
import com.youyu.cardequity.payment.biz.enums.RouteVoIdFlagEnum;
import com.youyu.cardequity.payment.biz.service.MockReconciliationDataService;
import com.youyu.cardequity.payment.dto.MockReconciliationDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;
import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByName;
import static com.youyu.cardequity.common.base.util.DateUtil.*;
import static com.youyu.cardequity.common.base.util.LocalDateUtils.date2LocalDateTime;
import static com.youyu.cardequity.common.base.util.UuidUtil.uuid4NoRail;
import static com.youyu.cardequity.payment.dto.PayLogResponseDto.STATUS_PAYMENT_SUCC;
import static com.youyu.cardequity.payment.dto.PayTradeRefundResponseDto.STATUS_SUCC;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.time.DateUtils.addDays;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work mock对账造数据方便测试测 Service impl 注:仅用给测试造数据使用
 */
@Service
public class MockReconciliationDataServiceImpl implements MockReconciliationDataService {

    @Autowired
    private BatchService batchService;

    @Override
    @Transactional
    public void mockTradeData(MockReconciliationDataDto mockReconciliationDataDto) {
        List<PayLog> payLogs = new ArrayList<>();
        List<TradeOrder> tradeOrders = new ArrayList<>();
        List<PayCheckFileDeatail> payCheckFileDeatails = new ArrayList<>();

        Integer quantity = mockReconciliationDataDto.getQuantity();
        for (int i = 0; i < quantity; i++) {
            PayLog payLog = mockPayLog(mockReconciliationDataDto);
            TradeOrder tradeOrder = mockTradeOrder(payLog);
            PayCheckFileDeatail payCheckFileDeatail = mockPayCheckFileDeatail(payLog);

            payLogs.add(payLog);
            tradeOrders.add(tradeOrder);
            if (nonNull(payCheckFileDeatail)) {
                payCheckFileDeatails.add(payCheckFileDeatail);
            }
        }

        batchService.batchDispose(payLogs, PayLogMapper.class, "insertSelective");
        batchService.batchDispose(tradeOrders, TradeOrderMapper.class, "insertSelective");
        batchService.batchDispose(payCheckFileDeatails, PayCheckFileDeatailMapper.class, "insertSelective");
    }

    @Override
    public void mockRefundData(MockReconciliationDataDto mockReconciliationDataDto) {
        List<PayLog> payLogs = new ArrayList<>();
        List<TradeOrder> tradeOrders = new ArrayList<>();
        List<PayCheckFileDeatail> payCheckFileDeatails = new ArrayList<>();
        List<PayTradeRefund> payTradeRefunds = new ArrayList<>();

        Integer quantity = mockReconciliationDataDto.getQuantity();
        for (int i = 0; i < quantity; i++) {
            PayLog payLog = mockPayLog(mockReconciliationDataDto);
            PayTradeRefund payTradeRefund = mockPayTradeRefund(payLog);
            TradeOrder tradeOrder = mockTradeOrder(payLog, payTradeRefund);
            PayCheckFileDeatail payCheckFileDeatail = mockPayCheckFileDeatail(payLog, payTradeRefund);

            payLogs.add(payLog);
            payTradeRefunds.add(payTradeRefund);
            tradeOrders.add(tradeOrder);
            if (nonNull(payCheckFileDeatail)) {
                payCheckFileDeatails.add(payCheckFileDeatail);
            }
        }

        batchService.batchDispose(payLogs, PayLogMapper.class, "insertSelective");
        batchService.batchDispose(payTradeRefunds, PayTradeRefundMapper.class, "insertSelective");
        batchService.batchDispose(tradeOrders, TradeOrderMapper.class, "insertSelective");
        batchService.batchDispose(payCheckFileDeatails, PayCheckFileDeatailMapper.class, "insertSelective");
    }

    private PayCheckFileDeatail mockPayCheckFileDeatail(PayLog payLog, PayTradeRefund payTradeRefund) {
        PayCheckFileDeatail payCheckFileDeatail = mockPayCheckFileDeatail(payLog);
        if (Objects.isNull(payCheckFileDeatail)) {
            return null;
        }

        payCheckFileDeatail.setRefundBatchNo(payTradeRefund.getRefundNo());
        payCheckFileDeatail.setReturnStatus(STATUS_SUCC);
        return payCheckFileDeatail;
    }

    private TradeOrder mockTradeOrder(PayLog payLog, PayTradeRefund payTradeRefund) {
        TradeOrder tradeOrder = mockTradeOrder(payLog);
        tradeOrder.setPayRefundNo(payTradeRefund.getRefundNo());
        tradeOrder.setPayRefundId(payTradeRefund.getId());
        tradeOrder.setRefundAmount(payTradeRefund.getRefundAmount());
        tradeOrder.setRefundStatus(payTradeRefund.getRefundStatus());
        return tradeOrder;
    }

    private PayTradeRefund mockPayTradeRefund(PayLog payLog) {
        PayTradeRefund payTradeRefund = new PayTradeRefund4Alipay();
        payTradeRefund.setPayLogId(payLog.getId());
        payTradeRefund.setAppSheetSerialNo(payLog.getAppSheetSerialNo());
        payTradeRefund.setRefundApplyAmount(payLog.getOccurBalance());
        payTradeRefund.setRefundAmount(payLog.getOccurBalance());
        payTradeRefund.setRefundNo(uuid4NoRail());
        payTradeRefund.setRefundReason("买错了");
        payTradeRefund.setStatus(randomPayTradeRefundStatus(4));
        payTradeRefund.setClientId(payLog.getClientId());
        payTradeRefund.setClientName(payLog.getClientName());
        payTradeRefund.setChannelNo("000001");
        payTradeRefund.setType("1");
        return payTradeRefund;
    }

    private PayCheckFileDeatail mockPayCheckFileDeatail(PayLog payLog) {
        if (random(5) <= 1) {
            return null;
        }
        PayCheckFileDeatail payCheckFileDeatail = new PayCheckFileDeatail();
        payCheckFileDeatail.setTranceNo(UuidUtil.uuid4NoRail());
        payCheckFileDeatail.setChannelNo("000001");
        payCheckFileDeatail.setCheckDate(date2String(addDays(now(), -1), YYYYMMDD));
        payCheckFileDeatail.setFileId(UuidUtil.uuid4NoRail());
        payCheckFileDeatail.setAppDate(date2String(addDays(now(), -1), YYYYMMDD));
        payCheckFileDeatail.setAppAmount(payLog.getOccurBalance());
        payCheckFileDeatail.setPayState(STATUS_PAYMENT_SUCC);
        payCheckFileDeatail.setAppSheetSerialNo(payLog.getAppSheetSerialNo());
        payCheckFileDeatail.setBusinType("1");
        payCheckFileDeatail.setRemark("mock数据");
        payCheckFileDeatail.setOrderAmount(payLog.getOccurBalance());
        return payCheckFileDeatail;
    }

    private TradeOrder mockTradeOrder(PayLog payLog) {
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setAppSheetSerialNo(payLog.getAppSheetSerialNo());
        tradeOrder.setTransAccountId(payLog.getTransAccountId());
        tradeOrder.setClientId(payLog.getClientId());
        tradeOrder.setClientName(payLog.getClientName());
        tradeOrder.setPayChannelNo(payLog.getPayChannelNo());
        tradeOrder.setOrderAmount(payLog.getOccurBalance());
        tradeOrder.setPayState(payLog.getPayState());
        tradeOrder.setPayLogId(payLog.getId());
        tradeOrder.setSyncDataDate(date2String(now(), YYYYMMDD));
        tradeOrder.setBusinCode(payLog.getBusinCode());
        return tradeOrder;
    }

    private PayLog mockPayLog(MockReconciliationDataDto mockReconciliationDataDto) {
        PayLog payLog = new PayLog4Alipay();
        payLog.setInitDate(LocalDate.now());
        payLog.setTransActionDate(LocalDate.now());
        payLog.setAppSheetSerialNo(uuid4NoRail());
        payLog.setBankCode("1");
        payLog.setClientId(uuid4NoRail());
        payLog.setClientName("有鱼");

        payLog.setOccurBalance(randomBalance(2000));
        payLog.setPayChannelNo("000001");
        payLog.setRemark("mock数据");
        payLog.setThirdSerialNo(uuid4NoRail());
        payLog.setRouteVoIdFlag(RouteVoIdFlagEnum.NORMAL.getCode());
        payLog.setType("1");
        payLog.setTradeCloseFlag(false);
        Date date = addDays(now(), -1);
        payLog.setCreateTime(date2LocalDateTime(date));
        payLog.setUpdateTime(date2LocalDateTime(date));

        if (mockReconciliationDataDto.getIsTrade()) {
            payLog.setState(randomPayLogStatus(4));
        } else {
            payLog.setState(getBeanByClass(PayLogStatus4PaymentQueuing.class));
        }
        return payLog;
    }

    private BigDecimal randomBalance(int i) {
        Random random = new Random();
        int balance = random.nextInt(i);
        return new BigDecimal(balance);
    }

    private PayLogStatus randomPayLogStatus(int i) {
        Random random = new Random();
        int status = random.nextInt(i) % (i - 1) + 2;
        return (PayLogStatus) getBeanByName(PayLogStatus.class.getSimpleName() + status);
    }

    private PayTradeRefundStatus randomPayTradeRefundStatus(int i) {
        Random random = new Random();
        int status = random.nextInt(i) % (i - 1) + 2;
        return (PayTradeRefundStatus) getBeanByName(PayTradeRefundStatus.class.getSimpleName() + status);
    }

    private int random(int i) {
        Random random = new Random();
        int number = random.nextInt(i);
        return number;
    }
}
