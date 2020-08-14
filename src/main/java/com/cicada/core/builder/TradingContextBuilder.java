package com.cicada.core.builder;

import com.cicada.core.bean.*;
import com.cicada.core.enums.*;
import com.cicada.core.exception.business.IllegalPayParameterException;
import com.cicada.storage.PayPersistService;
import com.cicada.utils.IDCardUtil;
import com.cool.core.RequestContext;
import com.dotin.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author WUJXIAO
 * @version 1.0
 * @create 2019-01-02 20:10
 */
@Component
public class TradingContextBuilder {

    @Autowired
    private PayPersistService persistService;

    public TradingContext build(RequestContext req) {
        TradingContext context = new TradingContext();
        //request info
        context.setCreateTime(req.getRequestTime());
        context.setMethod(req.getStr(TradingParameter.METHOD));
        context.setVersion(req.getStr(TradingParameter.VERSION));
        context.setChannel(Channel.from(req.getStr(TradingParameter.CHANNEL)));
        context.setMessage(new TradingMessage(req.getParams()));
        context.getMessage().setRequestTime(new Date(context.getCreateTime()));
        context.getMessage().setReqReserved(req.getStr(TradingParameter.RESERVED));

        //交易结果初始化
        context.setResult(new TradingResult(TradingStatus.PENDING));

        //core
        context.setClientIp(req.getStr(TradingParameter.CLIENT_IP));
        context.setDevice(req.getStr(TradingParameter.DERVICE));
        context.setNotifyUrl(req.getStr(TradingParameter.NOTIFY_URL));
        context.setReturnUrl(req.getStr(TradingParameter.RETURN_URL));
        context.setQuitUrl(req.getStr(TradingParameter.QUIT_URL));

        //application info
        Application application = new Application();
        application.setAppId(req.getStr(TradingParameter.APP_ID));
        application.setMchId(req.getStr(TradingParameter.MCH_ID));
        application.setChannel(context.getChannel());
        context.setApplication(application);

        //trading order
        Integer amount = req.getInt(TradingParameter.Order.AMOUNT);
        TradingOrder order = new TradingOrder();
        order.setOrderId(req.getStr(TradingParameter.Order.ID));
        order.setOrderTime(req.getDate(TradingParameter.Order.TIME));
        order.setSubject(req.getStr(TradingParameter.Order.SUBJECT));
        order.setBody(req.getStr(TradingParameter.Order.BODY));
        order.setCurrency(Currency.from(req.getStr(TradingParameter.Order.CURRENCY), Currency.CNY));
        order.setAmount(amount);
        context.setOrder(order);

        //trading extra
        TradingExtra extra = new TradingExtra();
        extra.setAuthCode(req.getStr(TradingParameter.AUTH_CODE));
        extra.setOpenId(req.getStr(TradingParameter.OPEN_ID));
        extra.setSmsType(SmsType.from(req.getStr(TradingParameter.SMS_TYPE)));
        extra.setBillDate(req.getDate(TradingParameter.BILL_DATE));
        context.setExtra(extra);

        //card info
        String bindId = req.getStr(TradingParameter.Card.BIND_ID);
        TradingCard card = null;
        if (StringUtils.isEmpty(bindId)) {
            String cardNo = req.getStr(TradingParameter.Card.NO);
            String bankCode = req.getStr(TradingParameter.Card.ORGANIZATION_CODE);
            if (StringUtils.isNotEmpty(cardNo) || StringUtils.isNotEmpty(bankCode)) {
                card = new TradingCard();
                card.setNo(cardNo);
                card.setType(CardType.from(req.getStr(TradingParameter.Card.TYPE)));
                card.setHolderName(req.getStr(TradingParameter.Card.HOLDER_NAME));
                card.setMobileNo(req.getStr(TradingParameter.Card.CARD_MOBILE_NO));
                card.setCertificationType(CertificationType.from(req.getStr(TradingParameter.Card.CERTIFICATION_TYPE)));
                card.setCertificationNo(req.getStr(TradingParameter.Card.CERTIFICATION_NO));
                if (null != card.getCertificationNo() && null == card.getCertificationType()) {
                    card.setCertificationType(CertificationType.IDENTIFICATION);//默认身份证
                }
                if (CertificationType.IDENTIFICATION.equals(card.getCertificationType())) {
                    if (!IDCardUtil.isIDCard(card.getCertificationNo())) {
                        throw new IllegalPayParameterException("证件号码错误，请输入合法证件号");
                    }
                }
                card.setCvv(req.getStr(TradingParameter.Card.CREDITCARD_CVV));
                card.setExpireMonth(req.getStr(TradingParameter.Card.CREDITCARD_EXPIRE_MONTH));
                card.setExpireYear(req.getStr(TradingParameter.Card.CREDITCARD_EXPIRE_YEAR));
                card.setOrgCode(bankCode);
            }
        } else {
            //绑卡信息溯源
            card = persistService.getCard(bindId, context.getApplication().getAppId());
        }
        context.setCard(card);

        String recordId = req.getStr(TradingParameter.RECORD_ID);
        //原单据数据溯源处理
        if (StringUtils.isNotEmpty(recordId)) {
            TradingContext targetContext = persistService.getContext(recordId, application.getMchId());
            context.setTarget(targetContext);
            context.setChannel(targetContext.getChannel());
            application.setAppId(targetContext.getApplication().getAppId());
            application.setChannel(targetContext.getApplication().getChannel());
            copyOrder(targetContext.getOrder(), order);
        }
        return context;
    }

    /**
     * 主订单数据溯源
     *
     * @param org
     * @param tag
     */
    private void copyOrder(TradingOrder org, TradingOrder tag) {
        if (null != org && null != tag) {
            if (null == tag.getOrderId()) tag.setOrderId(org.getOrderId());
            if (null == tag.getOrderTime()) tag.setOrderTime(org.getOrderTime());
            if (null == tag.getSubject()) tag.setSubject(org.getSubject());
            if (null == tag.getBody()) tag.setBody(org.getBody());
            if (null == tag.getCurrency()) tag.setCurrency(org.getCurrency());
            if (null == tag.getAmount()) tag.setAmount(org.getAmount());
        }
    }

}