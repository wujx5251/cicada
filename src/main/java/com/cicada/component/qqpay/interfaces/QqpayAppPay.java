package com.cicada.component.qqpay.interfaces;

import com.cicada.component.qqpay.QqpayAbstractPay;
import com.cicada.component.qqpay.bean.QqResponse;
import com.cicada.component.qqpay.bean.QqpayConfig;
import com.cicada.component.qqpay.enums.QqpayParam;
import com.cicada.component.qqpay.enums.QqpayTradeType;
import com.cicada.component.qqpay.support.QqpaySecurity;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.enums.TradingStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * qq App支付
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.APP_PAY, channel = Channel.QQPAY, product = ProductType.APP)
public class QqpayAppPay extends QqpayAbstractPay {

    @Override
    protected String getTradeType() {
        return QqpayTradeType.APP.value();
    }

    @Override
    protected TradingResult render(TradingContext context, TradingResult result) {
        QqpayConfig config = context.getConfig();
        Map<String, Object> params = new HashMap<String, Object>(7);
        params.put(QqpayParam.APP_ID.value(), config.getAppId());
        params.put(QqpayParam.PARTNERID.value(), config.getMchId());
        params.put(QqpayParam.PREPAYID.value(), ((QqResponse) result.getData()).getPrepayId());
        params.put(QqpayParam.NONCESTR.value(), ((QqResponse) result.getData()).getNonceStr());
        params.put(QqpayParam.PACKAGE.value(), "Sign=WXPay");
        params.put(QqpayParam.TIMESTAMP.value(), context.getCreateTime() / 1000);
        params.put(QqpayParam.SIGN.value(), QqpaySecurity.instance.sign(config, params));

        return result.setStatus(TradingStatus.PENDING).
                setAmount(context.getOrder().getAmount()).
                setData(params);
    }

}