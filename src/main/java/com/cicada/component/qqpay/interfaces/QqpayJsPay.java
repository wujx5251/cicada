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
import com.dotin.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * qq 统一支付 JSAPI
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.JS_PAY, channel = Channel.QQPAY, product = ProductType.JSAPI)
public class QqpayJsPay extends QqpayAbstractPay {

    @Override
    protected String getTradeType() {
        return QqpayTradeType.JSAPI.value();
    }

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = super.getParams(context);
        params.put(QqpayParam.OPEN_ID.value(), context.getExtra().getOpenId());
        return params;
    }

    @Override
    protected TradingResult render(TradingContext context, TradingResult result) {
        QqpayConfig config = context.getConfig();
        Map<String, Object> params = new HashMap<String, Object>(6);
        params.put(QqpayParam.APPID.value(), config.getAppId());
        params.put(QqpayParam.NONCESTR_JS.value(), ((QqResponse) result.getData()).getNonceStr());
        params.put(QqpayParam.PACKAGE.value(), "prepay_id=" + ((QqResponse) result.getData()).getPrepayId());
        params.put(QqpayParam.TIMESTAMP_JS.value(), StringUtils.parse(context.getCreateTime() / 1000));
        params.put(QqpayParam.SIGNTYPE.value(), config.getSignType());
        params.put(QqpayParam.PAYSIGN.value(), QqpaySecurity.instance.sign(config, params));
        return new TradingResult(TradingStatus.PENDING).
                setAmount(context.getOrder().getAmount()).
                setData(params);
    }

}