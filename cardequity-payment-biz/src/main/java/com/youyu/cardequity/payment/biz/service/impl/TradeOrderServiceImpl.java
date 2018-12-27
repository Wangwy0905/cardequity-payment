package com.youyu.cardequity.payment.biz.service.impl;

import com.youyu.cardequity.common.spring.service.BatchService;
import com.youyu.cardequity.payment.biz.dal.dao.TradeOrderMapper;
import com.youyu.cardequity.payment.biz.dal.entity.TradeOrder;
import com.youyu.cardequity.payment.biz.service.TradeOrderService;
import com.youyu.cardequity.payment.dto.TradeOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.alibaba.fastjson.JSON.parseArray;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 交易订单退款信息Service Impl
 */
@Service
public class TradeOrderServiceImpl implements TradeOrderService {

    @Autowired
    private BatchService batchService;

    @Override
    @Transactional
    public void syncTradeOrderMessage(String json) {
        List<TradeOrderDto> tradeOrderDtos = parseArray(json, TradeOrderDto.class);
        List<TradeOrder> tradeOrders = new ArrayList<>();
        for (TradeOrderDto tradeOrderDto : tradeOrderDtos) {
            tradeOrders.add(new TradeOrder(tradeOrderDto));
        }

        // TODO: 2018/12/27  后续考虑先删后存
        batchService.batchDispose(tradeOrders, TradeOrderMapper.class, "insertSelective");
    }
}
