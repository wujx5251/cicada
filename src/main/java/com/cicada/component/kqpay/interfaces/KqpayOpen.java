package com.cicada.component.kqpay.interfaces;

import com.cicada.component.kqpay.KqpayAbstractReqPay;
import com.cicada.component.kqpay.bean.KqPartObject;
import com.cicada.component.kqpay.bean.KqResponse;
import com.cicada.component.kqpay.enums.KqCertificationType;
import com.cicada.component.kqpay.enums.KqNodeType;
import com.cicada.component.kqpay.enums.KqpayAction;
import com.cicada.component.kqpay.enums.KqpayParam;
import com.cicada.core.Pay;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingCard;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.enums.TradingStatus;
import com.cicada.core.exception.business.IllegalCardException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 快钱开通绑卡（根据商户配置，是否需求确认，需要确认需要使用KqpayOpenConfirm完成绑卡）
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.BIND_SMS_SEND, channel = Channel.KQPAY, product = ProductType.QUICK_PRE)
public class KqpayOpen extends KqpayAbstractReqPay {

    @Autowired
    private Pay kqpayCardQuery;

    @Override
    protected String getAction() {
        return KqpayAction.OPEN.value();
    }

    @Override
    protected String getNodeName() {
        return KqNodeType.IND_AUTH.value();
    }

    @Override
    protected KqPartObject getParam(TradingContext context) {
        TradingResult result = kqpayCardQuery.doPay(context);
        TradingCard card = context.getCard();
        if (TradingStatus.FAIL.equals(result.getStatus())) {
            throw new IllegalCardException(card.getNo());
        }
        KqResponse rsp = (KqResponse) result.getData();
        card.setOrgCode(rsp.getBankCode());
        card.setOrgName(rsp.getBankName());
        card.setType(rsp.getCardType());

        KqPartObject param = super.getParam(context);
        param.put(KqpayParam.ACC_NO.value(), card.getNo());
        param.put(KqpayParam.HOLDER_NAME.value(), card.getHolderName());
        param.put(KqpayParam.CERTIF_TP.value(), KqCertificationType.from(card.getCertificationType()));
        param.put(KqpayParam.CERTIF_ID.value(), card.getCertificationNo());
        param.put(KqpayParam.EXPRIRED.value(), card.getExpire(false));
        param.put(KqpayParam.PHONE_NO.value(), card.getMobileNo());
        param.put(KqpayParam.CVV2.value(), card.getCvv());
        param.put(KqpayParam.BIND_TYPE.value(), "0");
        param.remove(KqpayParam.SHORT_CARD_NO.value());
        return param;
    }

    @Override
    protected String getToken(TradingContext context) {
        return context.getRecordId();
    }

    @Override
    protected String getShortCardNo(TradingContext context) {
        return null;
    }
}