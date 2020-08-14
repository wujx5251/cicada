package com.cicada.component.kqpay.interfaces;

import com.cicada.Constants;
import com.cicada.component.kqpay.KqpayAbstractReqPay;
import com.cicada.component.kqpay.bean.KqPartObject;
import com.cicada.component.kqpay.enums.KqNodeType;
import com.cicada.component.kqpay.enums.KqTxnType;
import com.cicada.component.kqpay.enums.KqpayAction;
import com.cicada.component.kqpay.enums.KqpayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.dotin.cache.annotation.Cache;
import com.dotin.cache.annotation.CacheParam;

/**
 * 快钱卡信息查询
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.CARD_INFO, channel = Channel.KQPAY, product = ProductType.QUICK_PRE)
public class KqpayCardQuery extends KqpayAbstractReqPay {


    @Cache(key = "card_info_{#context.card.no}", condition = "#context.card!=null", ttl = Constants.DAY)
    @Override
    public TradingResult doPay(@CacheParam("context") TradingContext context) {
        return super.doPay(context);
    }

    @Override
    protected String getAction() {
        return KqpayAction.CARD_INFO.value();
    }

    @Override
    protected String getNodeName() {
        return KqNodeType.QRY_CARD.value();
    }

    @Override
    protected KqPartObject getParam(TradingContext context) {
        return KqPartObject.instance().
                put(KqpayParam.TXN_TYPE.value(), KqTxnType.PUR).
                put(KqpayParam.CARD_NO.value(), context.getCard().getNo());

    }
}