package com.cicada.component.kqpay.interfaces;

import com.cicada.component.kqpay.bean.KqPartObject;
import com.cicada.component.kqpay.enums.KqTxnType;
import com.cicada.component.kqpay.enums.KqpayAction;
import com.cicada.component.kqpay.enums.KqpayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.exception.business.IllegalTradeAmountException;
import com.cicada.utils.PayUtils;
import com.dotin.common.utils.NumberUtils;

/**
 * 快钱预授权完成支付
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.AUTH_CFM_PAY, channel = Channel.KQPAY, product = ProductType.AUTH_CONFIRM)
public class KqpayAuthCompletePay extends KqpayQuickPay {

    @Override
    protected String getAction() {
        return KqpayAction.AUTH_COMPLETE.value();
    }

    @Override
    protected KqPartObject getParam(TradingContext context) {
        KqPartObject param = super.getParam(context);
        param.put(KqpayParam.TXN_TYPE.value(), KqTxnType.CFM);
        if (null == context.getOrder().getAmount()) {
            param.put(KqpayParam.AMOUNT.value(), PayUtils.getCentMoney(context.getTarget().getOrder().getAmount()));
        } else if (NumberUtils.compare(context.getOrder().getAmount(), context.getTarget().getOrder().getAmount()) > 0) {
            throw new IllegalTradeAmountException();
        }

        param.put(KqpayParam.ORIG_REF_NUMBER.value(), context.getTarget().getReplyId());
        param.put(KqpayParam.AUTH_CODE.value(), context.getTarget().getExtra().getAuthCode());
        param.remove(KqpayParam.EXT_MAP.value());
        param.remove(KqpayParam.SP_FLAG.value());
        return param;
    }

    @Override
    protected String getShortCardNo(TradingContext context) {
        return null;
    }

    @Override
    protected String getToken(TradingContext context) {
        return null;
    }

    @Override
    protected void smsInfo(KqPartObject param, TradingContext target) {

    }
}