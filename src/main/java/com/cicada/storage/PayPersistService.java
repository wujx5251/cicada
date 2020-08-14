package com.cicada.storage;

import com.cicada.core.bean.Application;
import com.cicada.core.bean.TradingCard;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingOrder;
import com.cicada.core.enums.CardType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.enums.TradingStatus;
import com.cicada.core.exception.business.ApplicationNotFoundException;
import com.cicada.core.exception.business.CardNotFoundException;
import com.cicada.core.exception.business.ChannelConfigurationNotFoundException;
import com.cicada.core.exception.business.RecordNotFoundException;
import com.cicada.storage.bean.*;
import com.dotin.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class PayPersistService extends AbstractPersistService {

    @Transactional(noRollbackFor = {Throwable.class})
    public void save(TradingContext context) {
        saveRecord(context);
        saveDetail(context);
        if (null != context.getCard() &&
                null == context.getCard().getBindId() &&
                null != context.getCard().getCertificationNo()) {
            saveCardInfo(context);
        }
    }

    @Transactional(noRollbackFor = {Throwable.class})
    public void update(TradingContext context) {
        try {
            updateTrade(context);
        } finally {
            try {
                updateRecord(context);
            } finally {
                try {
                    updateDetail(context);
                } finally {
                    if (null != context.getCard() &&
                            null == context.getCard().getBindId() &&
                            null != context.getCard().getCertificationNo()) {
                        updateCardInfo(context);
                    }
                }
            }
        }
    }

    /**
     * 保存请求记录
     *
     * @param context
     * @return
     */
    public int updateRecord(TradingContext context) {
        RecordInfo recordInfo = toRecord(context);
        return recordInfoDao.update(recordInfo);
    }

    /**
     * 保存请求记录详情
     *
     * @param context
     * @return
     */
    public int updateDetail(TradingContext context) {
        RecordDetail detail = toRecordDetail(context.getMessage());
        detail.setRecordId(context.getRecordId());
        return recordDetailDao.update(detail);
    }

    /**
     * 更新支付记录
     *
     * @param context
     * @return
     */
    public int updateTrade(TradingContext context) {
        if (null != context.getProductType() &&
                !ProductType.NONE.value().equals(context.getProductType().value())) {
            TradeRecord recordInfo = toTradeRecord(context);
            return tradeRecordDao.update(recordInfo);
        }
        return 0;
    }

    /**
     * 保存请求记录
     *
     * @param context
     * @return
     */
    public int saveRecord(TradingContext context) {
        RecordInfo recordInfo = toRecord(context);
        recordInfo.setResultStatus(TradingStatus.PENDING.value());
        return recordInfoDao.insert(recordInfo);
    }

    /**
     * 保存请求记录
     *
     * @param context
     * @return
     */
    public int saveTrade(TradingContext context) {
        if (!ProductType.NONE.value().equals(context.getProductType().value())) {
            TradeRecord recordInfo = toTradeRecord(context);
            recordInfo.setStatus(TradingStatus.PENDING.value());
            return tradeRecordDao.insert(recordInfo);
        }
        return 0;
    }

    /**
     * 保存请求记录详情
     *
     * @param context
     * @return
     */
    public int saveDetail(TradingContext context) {
        RecordDetail detail = toRecordDetail(context.getMessage());
        detail.setRecordId(context.getRecordId());
        return recordDetailDao.insert(detail);
    }

    /**
     * 保存请求银行卡信息
     *
     * @param context
     * @return
     */
    public int saveCardInfo(TradingContext context) {
        if (null != context.getResult() &&
                !TradingStatus.SUCCESS.equals(context.getResult().getStatus())) {
            CardInfo cardInfo = toCardInfo(context.getCard());
            cardInfo.setRecordId(context.getRecordId());
            cardInfo.setCreateTime(new Date());
            return cardInfoDao.insert(cardInfo);
        }
        return 0;
    }

    /**
     * 保存请求银行卡信息
     *
     * @param context
     * @return
     */
    public int updateCardInfo(TradingContext context) {
        CardInfo cardInfo = new CardInfo();
        cardInfo.setBankCode(context.getCard().getOrgCode());
        cardInfo.setBankName(context.getCard().getOrgName());
        cardInfo.setRecordId(context.getRecordId());
        cardInfo.setModifyTime(new Date());
        return cardInfoDao.update(cardInfo);
    }


    /**
     * <p>根据recordNo恢复支付上下文</p>
     *
     * @param recordNo recordNo
     * @return 支付上下文
     */
    @Transactional(noRollbackFor = {Throwable.class})
    public TradingContext getContext(String recordNo, String mchId) {
        if (StringUtils.isEmpty(recordNo)) {
            return null;
        }
        RecordInfo recordInfo;
        if (StringUtils.isEmpty(mchId)) {
            recordInfo = recordInfoDao.loadById(recordNo);
        } else {
            recordInfo = recordInfoDao.load(recordNo, mchId);
        }

        if (recordInfo == null) {
            throw new RecordNotFoundException(recordNo);
        }

        TradeRecord record = tradeRecordDao.load(recordNo);
        CardInfo cardInfo = cardInfoDao.load(recordNo);

        TradingContext context = resumeContext(recordInfo);
        MerchantInfo info = merchantDao.load(context.getApplication().getMchId());
        context.getApplication().setMchKey(info.getMchKey());

        if (null != record) {
            context.setOrder(resumeOrder(record));
            context.setNotifyUrl(record.getNotifyUrl());
            context.setReturnUrl(record.getReturnUrl());
            context.setQuitUrl(record.getQuitUrl());
            context.setClientIp(record.getClientIp());

            if (!TradingStatus.FAIL.equals(context.getResult().getStatus())) {
                //状态在非失败情况先使用交易记录的详细状态
                context.getResult().setStatus(TradingStatus.from(record.getStatus()));
            }
        } else {//恢复请求记录的交易金额
            TradingOrder order = new TradingOrder();
            order.setAmount(recordInfo.getAmount());
            order.setTotalAmount(recordInfo.getAmount());
            order.setRefundAmount(0);
            context.setOrder(order);
        }

        if (null != cardInfo) {
            context.setCard(resumeCard(cardInfo));
        } else if (null != recordInfo.getBindId()) {//原交易绑卡id溯源绑卡信息
            context.setCard(getCard(recordInfo.getBindId(), recordInfo.getAppId()));
        }

        //递归溯源数据
        if (null != context.getTarget())
            context.setTarget(getContext(context.getTarget().getRecordId(), mchId));
        return context;
    }

    /**
     * <p>根据orderId恢复支付上下文</p>
     *
     * @param orderId orderId
     * @return 支付上下文
     */
    public TradingContext getContext(String orderId, Date orderTime, String mchId) {
        if (orderId == null || orderTime == null) {
            return null;
        }
        return null;
    }


    /**
     * 通过appid和产品信息恢复应用信息信息
     *
     * @param context
     * @return
     */
    @Transactional(noRollbackFor = {Throwable.class})
    public Application getApplication(TradingContext context) {
        if (context.getApplication().getAppId() == null) {
            return null;
        }
        MerchantApp app = appDao.load(context.getApplication().getAppId());
        if (app == null || (null != context.getApplication().getMchId() &&
                !StringUtils.isEquals(app.getMchId(), context.getApplication().getMchId()))
        ) {
            throw new ApplicationNotFoundException(context.getApplication().getAppId());
        }

        ChannelConfig config = new ChannelConfig();
        config.setAppId(context.getApplication().getAppId());
        config.setChannel(context.getChannel().value());
        config.setProduct(context.getProductType().value());
        config = configDao.load(config);

        if (config == null) {
            throw new ChannelConfigurationNotFoundException(context.getApplication().getAppId(),
                    context.getChannel().value(),
                    context.getProductType().value());
        }
        context.getApplication().setChannel(context.getChannel());
        context.getApplication().setcId(config.getId());
        context.getApplication().setSalt(config.getSalt());
        return context.getApplication();
    }

    /**
     * 恢复支付银行卡信息
     *
     * @param bindId 银行卡绑定id
     * @return 支付银行卡信息
     */
    public TradingCard getCard(String bindId, String appId) {
        if (bindId == null) {
            return null;
        }

        CardBind cardBind = new CardBind();
        cardBind.setBindId(bindId);
        cardBind.setAppId(appId);
        cardBind = cardBindDao.load(cardBind);
        if (cardBind == null) {
            throw new CardNotFoundException(bindId);
        }

        TradingCard card = new TradingCard();
        card.setBindId(cardBind.getBindId());
        card.setType(CardType.from(cardBind.getCardType()));
        card.setNo(cardBind.getShortCardNo());
        card.setToken(cardBind.getToken());
        return card;
    }
}
