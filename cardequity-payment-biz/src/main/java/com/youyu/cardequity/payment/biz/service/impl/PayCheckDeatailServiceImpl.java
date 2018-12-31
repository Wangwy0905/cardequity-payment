package com.youyu.cardequity.payment.biz.service.impl;

import com.youyu.cardequity.payment.biz.dal.dao.*;
import com.youyu.cardequity.payment.biz.dal.entity.*;
import com.youyu.cardequity.payment.biz.service.PayCheckDeatailService;
import com.youyu.cardequity.payment.dto.PayCheckDeatailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.youyu.cardequity.common.base.util.DateUtil.*;
import static com.youyu.cardequity.payment.biz.enums.RouteVoIdFlagEnum.NORMAL;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.replace;
import static org.apache.commons.lang3.time.DateUtils.addDays;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 对账信息管理 Service impl
 */
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
    public void reconciliation(PayCheckDeatailDto payCheckDeatailDto) {
        protectPayCheckDeatailDto(payCheckDeatailDto);

        // TODO: 2018/12/29 做事务切割,对于已经对账的文件和交易需要打标志
        bill2Trade(payCheckDeatailDto);

        trade2Bill(payCheckDeatailDto);

        // TODO: 2018/12/29 两天未查询到的则直接算支付失败由trade2Bill这个方法直接做掉
    }

    /**
     * 交易对账单
     *
     * @param payCheckDeatailDto
     */
    private void trade2Bill(PayCheckDeatailDto payCheckDeatailDto) {
        // TODO: 2018/12/28  查询未对账的前一天数据和由于日切导致的数据需要继续对账
        List<TradeOrder> tradeOrders = tradeOrderMapper.getByCreateNotReconciliation();
        for (TradeOrder tradeOrder : tradeOrders) {
            executeTrade2Bill(tradeOrder);
        }
    }

    private void executeTrade2Bill(TradeOrder tradeOrder) {
        String payRefundNo = tradeOrder.getPayRefundNo();
        if (isBlank(payRefundNo)) {
            doTrade2Bill(tradeOrder);
            return;
        }

        doTrade2BillRefund(tradeOrder);
    }

    private void doTrade2BillRefund(TradeOrder tradeOrder) {
        PayCheckFileDeatail payCheckFileDeatail = payCheckFileDeatailMapper.getByAppSeetSerialNoRefundBatchNo(tradeOrder.getAppSheetSerialNo(), tradeOrder.getPayRefundNo());
        if (isNull(payCheckFileDeatail)) {

            // TODO: 2018/12/28
            // 日切:注意,如果是失败的话，可能第二天也没有对账单
            // 非日切 退款失败


            return;
        }
        PayChannelInfo payChannelInfo = payChannelInfoMapper.getById(tradeOrder.getPayChannelNo());
        payChannelInfo.doTrade2BillRefund(tradeOrder, payCheckFileDeatail);
    }

    /**
     * // 对账单为空的时候有可能是由于日切导致的:比如日切是0点,那么第一天11:59做的交易可能在支付机构那边是第二天的12:01分,
     * 那么对账单有可能是第二天才会出现,如果第二天还没有出现对账单,则认为是支付失败的,相当于日切导致的数据,
     * 但是到支付机构那边连续两天都没有产生,则认为是支付失败的数据(因为有的支付机构只发送支付成功的数据!)
     * // 日切
     * // 非日切 交易失败
     *
     * @param tradeOrder
     */
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

    /**
     * 对账单对交易
     *
     * @param payCheckDeatailDto
     */
    private void bill2Trade(PayCheckDeatailDto payCheckDeatailDto) {
        List<PayCheckFileDeatail> payCheckFileDeatails = payCheckFileDeatailMapper.getListByBillDate(payCheckDeatailDto.getBillDate());
        for (PayCheckFileDeatail payCheckFileDeatail : payCheckFileDeatails) {
            executeBill2Trade(payCheckFileDeatail);
        }
    }

    private void executeBill2Trade(PayCheckFileDeatail payCheckFileDeatail) {
        String refundBatchNo = payCheckFileDeatail.getRefundBatchNo();
        if (isBlank(refundBatchNo)) {
            doBill2Trade(payCheckFileDeatail);
            return;
        }

        doBill2TradeRefund(payCheckFileDeatail);
    }

    private void doBill2Trade(PayCheckFileDeatail payCheckFileDeatail) {
        TradeOrder tradeOrder = tradeOrderMapper.getByAppSeetSerialNoPayRefundNoIsNull(payCheckFileDeatail.getAppSeetSerialNo());
        if (isNull(tradeOrder)) {
            // 文件交易单边
            PayLog payLog = payLogMapper.getByAppSheetSerialNoRouteVoIdFlag(payCheckFileDeatail.getAppSeetSerialNo(), NORMAL.getCode());
            PayCheckDeatail payCheckDeatail = new PayCheckDeatail(payCheckFileDeatail, payLog);
            payCheckDeatailMapper.insertSelective(payCheckDeatail);
            return;
        }

        PayChannelInfo payChannelInfo = payChannelInfoMapper.getById(tradeOrder.getPayChannelNo());
        payChannelInfo.doBill2Trade(payCheckFileDeatail, tradeOrder);
    }

    private void doBill2TradeRefund(PayCheckFileDeatail payCheckFileDeatail) {
        TradeOrder tradeOrder = tradeOrderMapper.getByAppSeetSerialNoPayRefundNo(payCheckFileDeatail.getAppSeetSerialNo(), payCheckFileDeatail.getRefundBatchNo());
        if (isNull(tradeOrder)) {
            // 文件退款单边
            PayTradeRefund payTradeRefund = payTradeRefundMapper.getByAppSheetSerialNoRefundNo(payCheckFileDeatail.getAppSeetSerialNo(), payCheckFileDeatail.getRefundBatchNo());
            PayCheckDeatail payCheckDeatail = new PayCheckDeatail(payCheckFileDeatail, payTradeRefund);
            payCheckDeatailMapper.insertSelective(payCheckDeatail);
            return;
        }

        PayChannelInfo payChannelInfo = payChannelInfoMapper.getById(tradeOrder.getPayChannelNo());
        payChannelInfo.doBill2TradeRefund(payCheckFileDeatail, tradeOrder);
    }

    private void protectPayCheckDeatailDto(PayCheckDeatailDto payCheckDeatailDto) {
        String billDate = payCheckDeatailDto.getBillDate();
        if (isBlank(billDate)) {
            billDate = date2String(addDays(now(), -1), YYYY_MM_DD);
        }
        payCheckDeatailDto.setBillDate(replace(billDate, "-", ""));
    }
}
