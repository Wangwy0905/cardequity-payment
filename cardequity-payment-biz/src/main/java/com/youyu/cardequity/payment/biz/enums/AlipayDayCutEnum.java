package com.youyu.cardequity.payment.biz.enums;

import com.youyu.cardequity.payment.biz.dal.dao.PayCheckDeatailMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayCheckDeatail;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.dal.entity.TradeOrder;
import lombok.Getter;

import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;

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
            PayCheckDeatail payCheckDeatail = new PayCheckDeatail(payLog, tradeOrder);
            payCheckDeatail.dayCut(payLog, tradeOrder);
            getBeanByClass(PayCheckDeatailMapper.class).insertSelective(payCheckDeatail);
        }
    },
    BEFORE_DAY_CUT("1", "前一天日切") {
        @Override
        public void doTrade2BillNotFile(PayLog payLog, TradeOrder tradeOrder) {
            PayCheckDeatail payCheckDeatail = getBeanByClass(PayCheckDeatailMapper.class).getByAppSeetSerialNo(tradeOrder.getAppSheetSerialNo());
            payCheckDeatail.beforeDayCut(payLog, tradeOrder);
            getBeanByClass(PayCheckDeatailMapper.class).updateByDoTrade2BillNotFile();
        }
    },
    NOT_DAY_CUT("2", "非日切") {
        @Override
        public void doTrade2BillNotFile(PayLog payLog, TradeOrder tradeOrder) {
            PayCheckDeatail payCheckDeatail = new PayCheckDeatail(payLog, tradeOrder);
            payCheckDeatail.notDayCut(payLog, tradeOrder);
            getBeanByClass(PayCheckDeatailMapper.class).insertSelective(payCheckDeatail);
        }
    };

    private String code;

    private String msg;

    AlipayDayCutEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public abstract void doTrade2BillNotFile(PayLog payLog, TradeOrder tradeOrder);
}
