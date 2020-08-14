package com.cicada.component.unionpay.interfaces;

import com.cicada.component.unionpay.UnionpayAbstractTokenPay;
import com.cicada.component.unionpay.enums.UnionpayCurrency;
import com.cicada.component.unionpay.enums.UnionpayParam;
import com.cicada.component.unionpay.enums.UnionpaySmsType;
import com.cicada.component.unionpay.enums.UnionpayTxnType;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;

import java.util.Map;

/**
 * 银联短信发送
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.SMS_SEND, channel = Channel.UNIONPAY, product = ProductType.QUICK_PRE)
public class UnionpaySms extends UnionpayAbstractTokenPay {

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = super.getParams(context);
        params.put(UnionpayParam.TXN_TYPE.value(), UnionpayTxnType.SEND_SMS);
        if (null != context.getExtra())
            params.put(UnionpayParam.TXN_SUB_TYPE.value(), UnionpaySmsType.from(context.getExtra().getSmsType()));
        else
            params.put(UnionpayParam.TXN_SUB_TYPE.value(), UnionpaySmsType.CONSUME);

        params.put(UnionpayParam.AMOUNT.value(), context.getOrder().getAmount());
        params.put(UnionpayParam.CURRENCY.value(), UnionpayCurrency.from(context.getOrder().getCurrency()));
        return params;
    }


}