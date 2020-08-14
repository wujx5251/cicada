package com.cicada.component.kqpay.interfaces;

import com.cicada.component.kqpay.bean.KqpayConfig;
import com.cicada.component.kqpay.enums.KqPayType;
import com.cicada.component.kqpay.enums.KqpayAction;
import com.cicada.component.kqpay.enums.KqpayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;

import java.util.Map;

/**
 * 快钱 H5支付
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.WAP_PAY, channel = Channel.KQPAY, product = ProductType.H5)
public class KqpayH5Pay extends KqpayWebPay {

    @Override
    protected String getAction() {
        return KqpayAction.WAP_PAY.value();
    }

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = super.getParams(context);
        KqpayConfig config = context.getConfig();
        params.put(KqpayParam.VERSION.value(), config.getVersion());
        if (params.containsKey(KqpayParam.BANK_ID.value())) {
            if (KqPayType.WEB_DEBIT.value().equals(KqpayParam.PAY_TYPE.value())) {
                params.put(KqpayParam.PAY_TYPE.value(), KqPayType.QUICK_DEBIT);
            } else {
                params.put(KqpayParam.PAY_TYPE.value(), KqPayType.QUICK_CREDIT);
            }
        }
        params.put(KqpayParam.MOBILE_TYPE.value(), "phone");//移动网关版本，phone代表手机版移动网关，pad代表平板移动网关，默认为phone
        return params;
    }
}
