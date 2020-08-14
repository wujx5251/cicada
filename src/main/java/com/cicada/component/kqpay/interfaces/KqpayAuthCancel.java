package com.cicada.component.kqpay.interfaces;

import com.cicada.component.kqpay.bean.KqPartObject;
import com.cicada.component.kqpay.enums.KqTxnType;
import com.cicada.component.kqpay.enums.KqpayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;

/**
 * 快钱预授权撤销
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.AUTH_CANCEL, channel = Channel.KQPAY, product = ProductType.CANCEL)
public class KqpayAuthCancel extends KqpayCancel {

    @Override
    protected KqPartObject getParam(TradingContext context) {
        KqPartObject param = super.getParam(context);
        param.put(KqpayParam.ORIG_TXN_TYPE.value(), KqTxnType.PRE);
        return param;
    }

/*
    @Override
    protected TradingResult render(TradingContext context, TradingResult result) {
        super.render(context, result);
        context.getOrder().setRefundAmount(null);
        return result;
    }*/

}