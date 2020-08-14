package com.cicada.storage;

import com.cicada.Constants;
import com.cicada.core.bean.*;
import com.cicada.core.enums.*;
import com.cicada.core.generate.Generator;
import com.cicada.storage.bean.*;
import com.cicada.storage.dao.*;
import com.dotin.common.utils.LocalUtil;
import com.dotin.common.utils.NumberUtils;
import com.dotin.common.utils.StringUtils;
import com.dotin.common.utils.crypto.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public abstract class AbstractPersistService {

    @Autowired
    protected MerchantAppDao appDao;
    @Autowired
    protected MerchantDao mchDao;
    @Autowired
    protected ChannelConfigDao configDao;
    @Autowired
    protected CardBindDao cardBindDao;
    @Autowired
    protected CardDao cardInfoDao;
    @Autowired
    protected RecordInfoDao recordInfoDao;
    @Autowired
    protected TradeRecordDao tradeRecordDao;
    @Autowired
    protected RecordDetailDao recordDetailDao;
    @Autowired
    protected MerchantDao merchantDao;
    @Autowired
    protected Generator generator;

    /**
     * 使用卡信息恢复交易卡数据
     *
     * @param cardInfo
     * @return
     */
    protected TradingCard resumeCard(CardInfo cardInfo) {
        if (null == cardInfo)
            return null;
        String key = Constants.SECURITY_KEY + cardInfo.getSalt();
        TradingCard card = new TradingCard();
        card.setId(cardInfo.getId());
        card.setType(CardType.from(cardInfo.getCardType()));
        card.setNo(SecurityUtils.decrypt(cardInfo.getCardNo(), key));
        card.setMobileNo(SecurityUtils.decrypt(cardInfo.getMobileNo(), key));
        card.setCertificationType(CertificationType.from(cardInfo.getCertificationType()));
        card.setCertificationNo(SecurityUtils.decrypt(cardInfo.getCertificationNo(), key));
        card.setHolderName(SecurityUtils.decrypt(cardInfo.getHolderName(), key));
        card.setOrgCode(cardInfo.getBankCode());
        if (StringUtils.isNotEmpty(cardInfo.getExpiredYear())) {
            card.setExpireYear(SecurityUtils.decrypt(cardInfo.getExpiredYear(), key));
        }
        if (StringUtils.isNotEmpty(cardInfo.getExpiredMonth())) {
            card.setExpireMonth(SecurityUtils.decrypt(cardInfo.getExpiredMonth(), key));
        }
        if (StringUtils.isNotEmpty(cardInfo.getCvv2())) {
            card.setCvv(SecurityUtils.decrypt(cardInfo.getCvv2(), key));
        }
        return card;
    }

    /**
     * 订单数据恢复
     *
     * @param record
     * @return
     */
    protected TradingOrder resumeOrder(TradeRecord record) {
        if (null == record)
            return null;
        TradingOrder order = new TradingOrder();
        order.setOrderId(record.getOrderId());
        order.setOrderTime(record.getOrderTime());
        order.setSubject(record.getSubject());
        order.setBody(record.getBody());
        order.setTotalAmount(record.getAmount());
        order.setRefundAmount(record.getRefundAmount());//退货总金额
        order.setAmount(record.getAmount() - record.getRefundAmount());//可使用金额
        order.setCurrency(Currency.from(record.getCurrency(), null));
        return order;
    }

    /**
     * 转换为请求交易记录
     *
     * @param info
     * @return
     */
    protected TradingContext resumeContext(RecordInfo info) {
        TradingContext context = new TradingContext();
        context.setRecordId(info.getRecordId());
        context.setReplyId(info.getReplyId());
        context.setMethod(info.getMethod());
        context.setVersion(info.getVersion());
        if (StringUtils.isNotEmpty(info.getTargetRecordId())) {
            TradingContext targetContext = new TradingContext();
            targetContext.setRecordId(info.getTargetRecordId());
            context.setTarget(targetContext);
        }
        context.setChannel(Channel.from(info.getChannel()));
        context.setProductType(ProductType.from(info.getProduct()));

        TradingMessage message = new TradingMessage(null);
        context.setMessage(message);
        message.setReqReserved(info.getResultMsg());
        message.setRequestTime(info.getRequestTime());
        message.setResponseTime(info.getResponseTime());
        message.setAgentRequestTime(info.getChannelRequestTime());
        message.setAgentResponseTime(info.getChannelResponseTime());

        TradingResult result = new TradingResult();
        result.setStatus(TradingStatus.from(info.getResultStatus()));
        result.setErrMsg(info.getResultMsg());
        context.setResult(result);

        TradingExtra extra = TradingExtra.build(info.getExtraData());
        context.setExtra(extra);

        Application application = new Application();
        context.setApplication(application);
        application.setMchId(info.getMchId());
        application.setAppId(info.getAppId());
        application.setChannel(context.getChannel());
        return context;
    }

    /**
     * 转换为卡信息数据实体
     *
     * @param card
     * @return
     */
    protected CardInfo toCardInfo(TradingCard card) {
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardType(card.getType().value());
        cardInfo.setSalt(generator.generateSalt(8));
        String key = Constants.SECURITY_KEY + cardInfo.getSalt();
        cardInfo.setCardNo(SecurityUtils.encrypt(card.getNo(), key));
        cardInfo.setHolderName(SecurityUtils.encrypt(card.getHolderName(), key));
        cardInfo.setBankCode(card.getOrgCode());
        cardInfo.setCertificationType(card.getCertificationType().value());
        cardInfo.setCertificationNo(SecurityUtils.encrypt(card.getCertificationNo(), key));
        cardInfo.setMobileNo(SecurityUtils.encrypt(card.getMobileNo(), key));
        if (StringUtils.isNotEmpty(card.getExpireYear())) {
            cardInfo.setExpiredYear(SecurityUtils.encrypt(card.getExpireYear(), key));
        }
        if (StringUtils.isNotEmpty(card.getExpireMonth())) {
            cardInfo.setExpiredMonth(SecurityUtils.encrypt(card.getExpireMonth(), key));
        }
        if (StringUtils.isNotEmpty(card.getCvv())) {
            cardInfo.setCvv2(SecurityUtils.encrypt(card.getCvv(), key));
        }
        return cardInfo;
    }

    /**
     * 转换为请求交易记录
     *
     * @param context
     * @return
     */
    protected RecordInfo toRecord(TradingContext context) {
        RecordInfo recordInfo = new RecordInfo();
        recordInfo.setRecordId(context.getRecordId());
        recordInfo.setMchId(context.getApplication().getMchId());
        recordInfo.setAppId(context.getApplication().getAppId());
        if (null != context.getConfig()) {
            recordInfo.setChannelMchId(context.getConfig().getMchId());
            recordInfo.setChannelAppId(context.getConfig().getAppId());
        }
        recordInfo.setMethod(context.getMethod());
        recordInfo.setVersion(context.getVersion());
        if (null != context.getTarget()) {
            recordInfo.setTargetRecordId(context.getTarget().getRecordId());
        }
        recordInfo.setChannel(context.getChannel().value());
        if (null != context.getProductType()) {
            recordInfo.setProduct(context.getProductType().name().toLowerCase());
        }
        if (null != context.getCard()) {
            recordInfo.setBindId(context.getCard().getBindId());
        }
        recordInfo.setServerIp(LocalUtil.LOCAL_IP);
        recordInfo.setAmount(context.getOrder().getAmount());
        recordInfo.setRequestTime(context.getMessage().getRequestTime());
        recordInfo.setResponseTime(context.getMessage().getResponseTime());
        recordInfo.setChannelRequestTime(context.getMessage().getAgentRequestTime());
        recordInfo.setChannelResponseTime(context.getMessage().getAgentResponseTime());
        recordInfo.setReplyId(context.getResult().getReplyId());
        recordInfo.setResultStatus(context.getResult().getStatus().value());
        recordInfo.setResultMsg(context.getResult().getErrMsg());
        recordInfo.setExtraData(context.getMessage().getExtraData());
        return recordInfo;
    }

    /**
     * 转换为交易记录
     *
     * @param context
     * @return
     */
    protected TradeRecord toTradeRecord(TradingContext context) {
        TradeRecord recordInfo = new TradeRecord();
        recordInfo.setRecordId(context.getRecordId());
        recordInfo.setOrderId(context.getOrder().getOrderId());
        recordInfo.setOrderTime(context.getOrder().getOrderTime());
        recordInfo.setSubject(context.getOrder().getSubject());
        recordInfo.setBody(context.getOrder().getBody());
        recordInfo.setAmount(context.getOrder().getAmount());
        recordInfo.setCurrency(context.getOrder().getCurrency().value());
        recordInfo.setStatus(context.getResult().getStatus().value());
        if (null != context.getOrder().getRefundAmount()) {
            recordInfo.setRefundAmount(context.getOrder().getRefundAmount());
            if (NumberUtils.compare(context.getOrder().getRefundAmount(), context.getOrder().getAmount()) >= 0) {
                recordInfo.setStatus(TradingStatus.CLOSED.value());//全部退款完毕，关闭交易
            }
        }
        recordInfo.setTradeType(context.getProductType().getTradeType().value());
        if (null != context.getCard()) {
            recordInfo.setBindId(context.getCard().getBindId());
        }
        recordInfo.setNotifyUrl(context.getNotifyUrl());
        recordInfo.setReturnUrl(context.getReturnUrl());
        recordInfo.setQuitUrl(context.getQuitUrl());
        recordInfo.setClientIp(context.getClientIp());
        if (TradingStatus.PENDING.equals(context.getResult().getStatus()) &&
                null != context.getConfig()) {
            recordInfo.setExpireTime(context.getConfig().getLastTime());
        } else if (TradingStatus.SUCCESS.equals(context.getResult().getStatus())) {
            recordInfo.setPayTime(context.getResult().getRspTime());
        }
        return recordInfo;
    }

    /**
     * 转换为回调记录
     *
     * @param context
     * @return
     */
    protected CallbackRecord toCallBackRecord(TradingContext context) {
        CallbackRecord record = new CallbackRecord();
        record.setRecordId(context.getRecordId());
        record.setStatus(context.getResult().getStatus().value());
        record.setFinish(2);
        record.setAgentNotifyData(context.getMessage().getAgentRequestData());
        record.setAgentNotifyTime(context.getMessage().getAgentRequestTime());
        record.setAgentReturnData(context.getMessage().getAgentResponseData());
        record.setAgentReturnTime(context.getMessage().getAgentResponseTime());
        record.setCallbackData(context.getMessage().getRequestData());
        record.setCreateTime(new Date());
        record.setSignKey(context.getApplication().getMchKey());
        record.setCallbackUrl(context.getNotifyUrl());
        return record;
    }

    /**
     * 转换为请求记录详情
     *
     * @param message
     * @return
     */
    protected RecordDetail toRecordDetail(TradingMessage message) {
        RecordDetail recordDetail = new RecordDetail();
        recordDetail.setReqReserved(message.getReqReserved());
        recordDetail.setRequestData(message.getRequestData());
        recordDetail.setRequestTime(message.getRequestTime());
        recordDetail.setChannelRequestData(message.getAgentRequestData());
        recordDetail.setChannelRequestTime(message.getAgentRequestTime());
        recordDetail.setResponseData(message.getResponseData());
        recordDetail.setResponseTime(message.getResponseTime());
        recordDetail.setChannelResponseData(message.getAgentResponseData());
        recordDetail.setChannelResponseTime(message.getAgentResponseTime());
        recordDetail.setPagePayData(message.getPagePayData());
        return recordDetail;
    }

}
