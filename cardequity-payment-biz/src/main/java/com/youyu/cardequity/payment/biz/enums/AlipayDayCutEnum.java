package com.youyu.cardequity.payment.biz.enums;

import com.youyu.cardequity.payment.biz.dal.dao.PayCheckDeatailMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayCheckDeatail;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.dal.entity.PayTradeRefund;
import com.youyu.cardequity.payment.biz.dal.entity.TradeOrder;
import com.youyu.common.exception.BizException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;
import static com.youyu.cardequity.common.base.util.DateUtil.*;
import static com.youyu.cardequity.common.base.util.LocalDateUtils.localDateTime2Date;
import static com.youyu.cardequity.common.base.util.StringUtil.eq;
import static com.youyu.cardequity.payment.enums.PaymentResultCodeEnum.ALIPAY_DAY_CUT_CANNOT_NULL;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNoneBlank;
import static org.apache.commons.lang3.time.DateUtils.addDays;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 日切枚举
 */
@Getter
public enum AlipayDayCutEnum {

    DAY_CUT("0", "日切") {
        @Override
        public void doTrade2BillNotFile(PayLog payLog, TradeOrder tradeOrder) {
            if (isExist(tradeOrder.getPayCheckDeatailId())) {
                return;
            }

            PayCheckDeatail payCheckDeatail = new PayCheckDeatail(payLog, tradeOrder);
            payCheckDeatail.dayCut4Trade(payLog, tradeOrder);
            tradeOrder.setPayCheckDeatailId(payCheckDeatail.getId());
            getBeanByClass(PayCheckDeatailMapper.class).insertSelective(payCheckDeatail);
        }

        @Override
        public void doTrade2BillRefundNotFile(PayTradeRefund payTradeRefund, TradeOrder tradeOrder) {
            if (isExist(tradeOrder.getPayCheckDeatailId())) {
                return;
            }

            PayCheckDeatail payCheckDeatail = new PayCheckDeatail(payTradeRefund, tradeOrder);
            payCheckDeatail.dayCut4Refund(payTradeRefund, tradeOrder);
            tradeOrder.setPayCheckDeatailId(payCheckDeatail.getId());
            getBeanByClass(PayCheckDeatailMapper.class).insertSelective(payCheckDeatail);
        }
    },
    BEFORE_DAY_CUT("1", "前一天日切:连续两天都没有对账单,日切导致的数据,连续两天都没有对账单,则认为失败,因为支付宝只发支付成功的") {
        @Override
        public void doTrade2BillNotFile(PayLog payLog, TradeOrder tradeOrder) {
            PayCheckDeatail payCheckDeatail = getBeanByClass(PayCheckDeatailMapper.class).getByAppSeetSerialNoRefundBatchNoIsNull(tradeOrder.getAppSheetSerialNo());
            payCheckDeatail.beforeDayCut4Trade(payLog, tradeOrder);
            tradeOrder.setPayCheckDeatailId(payCheckDeatail.getId());
            getBeanByClass(PayCheckDeatailMapper.class).updateByDoTradeAndReturn2BillNotFile(payCheckDeatail);
        }

        @Override
        public void doTrade2BillRefundNotFile(PayTradeRefund payTradeRefund, TradeOrder tradeOrder) {
            PayCheckDeatail payCheckDeatail = getBeanByClass(PayCheckDeatailMapper.class).getByAppSeetSerialNoRefundBatchNo(tradeOrder.getAppSheetSerialNo(), tradeOrder.getPayRefundNo());
            payCheckDeatail.beforeDayCut4Refund(payTradeRefund, tradeOrder);
            tradeOrder.setPayCheckDeatailId(payCheckDeatail.getId());
            getBeanByClass(PayCheckDeatailMapper.class).updateByDoTradeAndReturn2BillNotFile(payCheckDeatail);
        }
    },
    NOT_DAY_CUT("2", "非日切") {
        @Override
        public void doTrade2BillNotFile(PayLog payLog, TradeOrder tradeOrder) {
            PayCheckDeatail payCheckDeatail = new PayCheckDeatail(payLog, tradeOrder);
            payCheckDeatail.notDayCut4Trade(payLog, tradeOrder);
            tradeOrder.setPayCheckDeatailId(payCheckDeatail.getId());
            getBeanByClass(PayCheckDeatailMapper.class).insertSelective(payCheckDeatail);
        }

        @Override
        public void doTrade2BillRefundNotFile(PayTradeRefund payTradeRefund, TradeOrder tradeOrder) {
            PayCheckDeatail payCheckDeatail = new PayCheckDeatail(payTradeRefund, tradeOrder);
            payCheckDeatail.notDayCut4Refund(payTradeRefund, tradeOrder);
            tradeOrder.setPayCheckDeatailId(payCheckDeatail.getId());
            getBeanByClass(PayCheckDeatailMapper.class).insertSelective(payCheckDeatail);
        }
    };

    private String code;

    private String msg;

    AlipayDayCutEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static AlipayDayCutEnum getAlipayDayCutEnumByTime(LocalDateTime time) {
        if (isNull(time)) {
            throw new BizException(ALIPAY_DAY_CUT_CANNOT_NULL);
        }

        String payTimeStr = time.toLocalDate().toString();
        String reconciliationDateBeforeDayStr = date2String(addDays(now(), -1), YYYY_MM_DD);
        if (!eq(payTimeStr, reconciliationDateBeforeDayStr)) {
            return BEFORE_DAY_CUT;
        }

        Date createDate = localDateTime2Date(time);
        Date dayCutPoint = string2Date(payTimeStr + " 23:50:00", YYYY_MM_DD_HH_MM_SS);
        return createDate.compareTo(dayCutPoint) >= 0 ? DAY_CUT : NOT_DAY_CUT;
    }

    public boolean isExist(String payCheckDeatailId) {
        return isNoneBlank(payCheckDeatailId);
    }

    public abstract void doTrade2BillNotFile(PayLog payLog, TradeOrder tradeOrder);

    public abstract void doTrade2BillRefundNotFile(PayTradeRefund payTradeRefund, TradeOrder tradeOrder);
}
