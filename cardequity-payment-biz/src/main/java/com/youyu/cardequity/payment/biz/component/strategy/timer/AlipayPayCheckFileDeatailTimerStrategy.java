package com.youyu.cardequity.payment.biz.component.strategy.timer;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.service.PayCheckFileDeatailService;
import com.youyu.cardequity.payment.dto.PayCheckFileDeatailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.youyu.cardequity.payment.dto.PayLogDto.PAY_CHANNEL_NO_ALIPAY;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 支付宝对账单定时下载策略
 */
@StatusAndStrategyNum(superClass = TimerStrategy.class, number = "200", describe = "支付宝对账单定时下载策略")
@Component
public class AlipayPayCheckFileDeatailTimerStrategy extends TimerStrategy {

    @Autowired
    private PayCheckFileDeatailService payCheckFileDeatailService;

    @Override
    public void execute() {
        PayCheckFileDeatailDto payCheckFileDeatailDto = new PayCheckFileDeatailDto();
        payCheckFileDeatailDto.setChannelNo(PAY_CHANNEL_NO_ALIPAY);
        payCheckFileDeatailDto.setBillDate("2019-03-29");
        payCheckFileDeatailService.downloadBill(payCheckFileDeatailDto);
    }
}
