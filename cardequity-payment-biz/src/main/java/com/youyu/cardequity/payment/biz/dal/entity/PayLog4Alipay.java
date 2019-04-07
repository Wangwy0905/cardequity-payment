package com.youyu.cardequity.payment.biz.dal.entity;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.youyu.cardequity.common.base.util.MoneyUtil;
import com.youyu.cardequity.payment.biz.component.command.paylog.PayLogCommand4AlipayAsyncMessage;
import com.youyu.cardequity.payment.biz.component.command.paylog.PayLogCommand4AlipaySyncMessage;
import com.youyu.cardequity.payment.biz.component.command.paylog.PayLogCommand4AlipayTradeClose;
import com.youyu.cardequity.payment.biz.component.command.paylog.PayLogCommand4TimeAlipayTradeQuery;
import com.youyu.cardequity.payment.biz.component.rabbitmq.RabbitmqSender;
import com.youyu.cardequity.payment.dto.PayLogAsyncMessageDto;
import com.youyu.cardequity.payment.dto.PayLogDto;
import com.youyu.cardequity.payment.dto.TradeCloseDto;
import com.youyu.cardequity.payment.dto.alipay.AlipaySyncMessageDto;
import com.youyu.cardequity.payment.dto.alipay.AlipaySyncMessageResponseDto;
import com.youyu.common.exception.BizException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Map;

import static com.alibaba.fastjson.JSON.toJSONString;
import static com.alipay.api.internal.util.AlipaySignature.rsaCheckV1;
import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;
import static com.youyu.cardequity.common.base.converter.BeanPropertiesConverter.copyProperties;
import static com.youyu.cardequity.common.base.util.CommonUtils.matches;
import static com.youyu.cardequity.common.base.util.MoneyUtil.betweenLeftRight;
import static com.youyu.cardequity.common.base.util.MoneyUtil.string2BigDecimal;
import static com.youyu.cardequity.common.base.util.StringUtil.*;
import static com.youyu.cardequity.payment.biz.enums.AlipayTradeStatusEnum.TRADE_FINISHED;
import static com.youyu.cardequity.payment.biz.enums.AlipayTradeStatusEnum.TRADE_SUCCESS;
import static com.youyu.cardequity.payment.biz.enums.RabbitmqMessageEnum.PAY_ASYNC_MESSAGE;
import static com.youyu.cardequity.payment.biz.enums.RouteVoIdFlagEnum.NORMAL;
import static com.youyu.cardequity.payment.biz.help.constant.AlipayConstant.*;
import static com.youyu.cardequity.payment.biz.help.constant.BusinessConstant.PAY_TYPE_ALIPAY;
import static com.youyu.cardequity.payment.enums.PaymentResultCodeEnum.*;
import static org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work AlipayPayLog支付日志
 */
@Slf4j
@Getter
@Setter
@Table(name = "TB_PAY_LOG")
public class PayLog4Alipay extends PayLog {

    private static final int ORDER_LENGTH_LIMIT = 64;
    private static final BigDecimal LEFT_AMOUNT = string2BigDecimal("0.01");
    private static final BigDecimal RIGHT_AMOUNT = string2BigDecimal("100000000");
    private static final String[] GOODS_TYPES = {"0", "1"};

    @Column(name = "ALIPAY_SUBJECT")
    private String alipaySubject;

    @Column(name = "ALIPAY_TIMEOUT_EXPRESS")
    private String alipayTimeoutExpress;

    @Column(name = "ALIPAY_GOODS_TYPE")
    private String alipayGoodsType;

    @Column(name = "ALIPAY_SYNC_MESSAGE")
    private String alipaySyncMessage;

    @Column(name = "ALIPAY_ASYNC_MESSAGE")
    private String alipayAsyncMessage;

    @Column(name = "ALIPAY_TRADE_STATUS")
    private String alipayTradeStatus;

    @Column(name = "ALIPAY_OUR_RESPONSE")
    private String alipayOurResponse;

    @Column(name = "ALIPAY_TRADE_CLOSE_MESSAGE")
    private String alipayTradeCloseMessage;

    @Column(name = "ALIPAY_TRADE_QUERY_MESSAGE")
    private String alipayTradeQueryMessage;

    private String returnUrl;

    public PayLog4Alipay() {
        super();
    }

    public PayLog4Alipay(PayLogDto payLogDto) {
        super(payLogDto);
        checkParameters(payLogDto);
        this.type = PAY_TYPE_ALIPAY;
        this.alipaySubject = payLogDto.getSubject();
        this.alipayTimeoutExpress = payLogDto.getTimeoutExpress();
        this.alipayGoodsType = payLogDto.getGoodsType();
        this.returnUrl = payLogDto.getReturnUrl();
    }

    public void alipaySyncMessage(AlipaySyncMessageDto alipaySyncMessageDto) {
        getBeanByClass(PayLogCommand4AlipaySyncMessage.class).executeCmd(this, alipaySyncMessageDto);
    }

    public String alipayAsyncMessage(Map<String, String> params2Map) {
        getBeanByClass(PayLogCommand4AlipayAsyncMessage.class).executeCmd(this, params2Map);
        return alipayOurResponse;
    }

    public void callPrepaymentSucc(String syncResponseBody) {
        this.respInfo = syncResponseBody;
        this.state = state.paymenting();
    }

    public void callPrepaymentFail(String syncResponseBody) {
        this.remark = syncResponseBody;
        this.state = state.paymentFail();
    }

    public void analysisAlipaySycnMessage(AlipaySyncMessageDto alipaySyncMessageDto, String sellerId, String appId) {
        this.alipaySyncMessage = toJSONString(alipaySyncMessageDto);

        /*String resultStatus = alipaySyncMessageDto.getResultStatus();
        ResultStatusEnum resultStatusEnum = getResultStatusEnum(resultStatus);
        if (resultStatusEnum.paySucc()) {
            AlipaySyncMessageResponseDto alipayTradeAppPayResponse = alipaySyncMessageDto.getAlipaySyncMessageResultDto().getAlipayTradeAppPayResponse();
            boolean flag = isCorrectSyncMessage(alipayTradeAppPayResponse, sellerId, appId);
            this.state = flag ? state.paymentSucc() : state.paymentFail();
            return;
        }

        this.state = state.paymentFail();*/
    }

    public void analysisAlipayAsyncMessage(Map<String, String> params2Map, String sellerId, String appId, String alipayPublicKey) {
        this.alipayAsyncMessage = toJSONString(params2Map);
        this.thirdSerialNo = params2Map.get(ALIPAY_TRADE_NO);
        this.alipayTradeStatus = params2Map.get(ALIPAY_TRADE_STATUS);
        this.alipayOurResponse = getAlipayOurResponse(params2Map, sellerId, appId, alipayPublicKey);
        if (matches(alipayTradeStatus, TRADE_SUCCESS.getCode(), TRADE_FINISHED.getCode())) {
            this.state = eq(alipayOurResponse, ALIPAY_ASYNC_RESPONSE_SUCC) ? state.paymentSucc() : state.paymentFail();
            this.routeVoIdFlag = state.isPaySucc() ? NORMAL.getCode() : this.routeVoIdFlag;
        }
    }

    public void callAlipayTradeCloseSucc(AlipayTradeCloseResponse alipayTradeCloseResponse) {
        this.alipayTradeCloseMessage = toJSONString(alipayTradeCloseResponse);
        this.tradeCloseFlag = alipayTradeCloseResponse.isSuccess();
    }

    public void analysisAlipayTradeQueryResponse(AlipayTradeQueryResponse alipayTradeQueryResponse) {
        this.alipayTradeQueryMessage = toJSONString(alipayTradeQueryResponse);
        this.alipayTradeStatus = alipayTradeQueryResponse.getTradeStatus();
        boolean tradeQueryFlag = alipayTradeQueryResponse.isSuccess();
        if (tradeQueryFlag) {
            this.state = matches(alipayTradeStatus, TRADE_SUCCESS.getCode(), TRADE_FINISHED.getCode()) ? state.paymentSucc() : state.paymentFail();
            return;
        }
        this.state = state.paymentFail();
    }

    @Override
    public void tradeClose(TradeCloseDto tradeCloseDto) {
        if (!canPayTradeClose()) {
            throw new BizException(PAYMENT_SUCCESS_ORDER_CANNOT_CLOSED);
        }

        getBeanByClass(PayLogCommand4AlipayTradeClose.class).executeCmd(this, tradeCloseDto);
    }

    public void callAlipayTradeCloseFail(String remark) {
        this.remark = remark;
        this.tradeCloseFlag = false;
    }

    @Override
    public void tradeQuery() {
        if (!canPayTradeQuery() || eq(alipayOurResponse, ALIPAY_ASYNC_RESPONSE_SUCC)) {
            return;
        }

        getBeanByClass(PayLogCommand4TimeAlipayTradeQuery.class).executeCmd(this, null);
    }

    public String getPrePayFlag() {
        return "1";
    }

    private void checkParameters(PayLogDto payLogDto) {
        String appSheetSerialNo = payLogDto.getAppSheetSerialNo();
        if (isBlank(appSheetSerialNo)) {
            throw new BizException(ORDER_NO_CANNOT_EMPTY);
        }

        if (checkParameterGtLength(appSheetSerialNo, ORDER_LENGTH_LIMIT)) {
            throw new BizException(ORDER_NO_DIGITS.getCode(), ORDER_NO_DIGITS.getFormatDesc(ORDER_LENGTH_LIMIT));
        }

        BigDecimal occurBalance = payLogDto.getOccurBalance();
        if (!betweenLeftRight(occurBalance, LEFT_AMOUNT, RIGHT_AMOUNT)) {
            throw new BizException(ORDER_AMOUNT_RANGE.getCode(), ORDER_AMOUNT_RANGE.getFormatDesc(LEFT_AMOUNT, RIGHT_AMOUNT));
        }

        String subject = payLogDto.getSubject();
        if (isBlank(subject)) {
            throw new BizException(ORDER_KEY_INFORMATION_CANNOT_EMPTY);
        }

        String goodsType = payLogDto.getGoodsType();
        if (!containParamByArray(goodsType, GOODS_TYPES)) {
            throw new BizException(ORDER_ITEM_MAIN_TYPE_CANNOT_EMPTY);
        }

    }

    private String getAlipayOurResponse(Map<String, String> params2Map, String sellerId, String appId, String alipayPublicKey) {
        try {
            boolean signFlag = rsaCheckV1(params2Map, alipayPublicKey, ALIPAY_CHARSET, ALIPAY_SIGN_TYPE);
            if (!signFlag) {
                return ALIPAY_ASYNC_RESPONSE_FAIL;
            }

            boolean paramVerifyFlag = isCorrectAsyncMessage(params2Map, sellerId, appId);
            if (!paramVerifyFlag) {
                return ALIPAY_ASYNC_RESPONSE_FAIL;
            }

            return ALIPAY_ASYNC_RESPONSE_SUCC;
        } catch (AlipayApiException e) {
            log.error("支付宝异步通知消息验签异常信息:[{}]和对应的原始json串:[{}]", getFullStackTrace(e), alipayAsyncMessage);
            return ALIPAY_ASYNC_RESPONSE_FAIL;
        }
    }

    private boolean isCorrectAsyncMessage(Map<String, String> params2Map, String sellerId, String appId) {
        String responseTotalAmount = params2Map.get(ALIPAY_TOTAL_AMOUNT);
        if (!MoneyUtil.eq(string2BigDecimal(responseTotalAmount), occurBalance)) {
            return false;
        }
        String responseSellerId = params2Map.get(ALIPAY_SELLER_ID);
        if (!eq(responseSellerId, sellerId)) {
            return false;
        }
        String responseAppId = params2Map.get(ALIPAY_APP_ID);
        if (!eq(responseAppId, appId)) {
            return false;
        }

        return true;
    }

    private boolean isCorrectSyncMessage(AlipaySyncMessageResponseDto alipayTradeAppPayResponse, String sellerId, String appId) {
        if (!MoneyUtil.eq(this.occurBalance, string2BigDecimal(alipayTradeAppPayResponse.getTotalAmount()))) {
            return false;
        }
        if (!eq(sellerId, alipayTradeAppPayResponse.getSellerId())) {
            return false;
        }
        if (!eq(appId, alipayTradeAppPayResponse.getAppId())) {
            return false;
        }

        return true;
    }

    public void sendMsg2Trade() {
        if (matches(alipayTradeStatus, TRADE_SUCCESS.getCode(), TRADE_FINISHED.getCode())) {
            PayLogAsyncMessageDto payLogAsyncMessageDto = copyProperties(this, PayLogAsyncMessageDto.class);
            String message = toJSONString(payLogAsyncMessageDto);
            log.info("异步通知交易系统支付宝支付对应的支付流水号:[{}]和消息信息:[{}]", this.id, message);
            getBeanByClass(RabbitmqSender.class).sendMessage(message, PAY_ASYNC_MESSAGE);
        }
    }
}
