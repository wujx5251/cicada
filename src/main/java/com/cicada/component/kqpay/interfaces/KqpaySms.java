package com.cicada.component.kqpay.interfaces;

import com.cicada.component.kqpay.KqpayAbstractReqPay;
import com.cicada.component.kqpay.bean.KqPartObject;
import com.cicada.component.kqpay.enums.KqNodeType;
import com.cicada.component.kqpay.enums.KqpayAction;
import com.cicada.component.kqpay.enums.KqpayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.utils.PayUtils;

/**
 * 快钱发送短信
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.SMS_SEND, channel = Channel.KQPAY, product = ProductType.QUICK_PRE)
public class KqpaySms extends KqpayAbstractReqPay {

    @Override
    protected String getAction() {
        return KqpayAction.SMS_SENDD.value();
    }

    @Override
    protected String getNodeName() {
        return KqNodeType.GET_DYN.value();
    }

    @Override
    protected KqPartObject getParam(TradingContext context) {
        KqPartObject param = super.getParam(context);
        param.put(KqpayParam.AMOUNT.value(), PayUtils.getCentMoney(context.getOrder().getAmount()));
        param.remove(KqpayParam.SHORT_CARD_NO.value());
        return param;
    }


}