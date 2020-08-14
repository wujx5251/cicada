package com.cicada.component.unionpay.interfaces;

import com.cicada.component.unionpay.UnionpayAbstractTokenPay;
import com.cicada.component.unionpay.enums.UnionpayParam;
import com.cicada.component.unionpay.enums.UnionpaySubTxnType;
import com.cicada.component.unionpay.enums.UnionpayTxnType;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;

import java.util.Map;

/**
 * 银联在线支付开通解除
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.BIND_REMOVE, channel = Channel.UNIONPAY, product = ProductType.QUICK_PRE)
public class UnionpayOpenRemove extends UnionpayAbstractTokenPay {

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = super.getParams(context);
        params.put(UnionpayParam.TXN_TYPE.value(), UnionpayTxnType.REMOVE_CARD);
        params.put(UnionpayParam.TXN_SUB_TYPE.value(), UnionpaySubTxnType.CONSUME);
        return params;
    }

}