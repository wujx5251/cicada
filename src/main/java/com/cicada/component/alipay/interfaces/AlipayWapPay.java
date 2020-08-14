package com.cicada.component.alipay.interfaces;

import com.cicada.component.alipay.bean.AlipayConfig;
import com.cicada.component.alipay.enums.AlipayMethod;
import com.cicada.component.alipay.enums.AlipayParam;
import com.cicada.component.alipay.support.AlipaySecurity;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.utils.PayUtils;
import com.cicada.utils.RequestUrlUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝支付-Wap支付(mapi版本)
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.WAP_PAY, channel = Channel.ALIPAY, product = ProductType.H5, version = "1.0.1")
public class AlipayWapPay extends AlipayWebPay {

    @Override
    public Map<String, ?> build(TradingContext context) {
        AlipayConfig config = context.getConfig();
        Map<String, Object> params = new HashMap<String, Object>(20);
        params.put("service", AlipayMethod.MWEB_PAY);
        params.put("partner", config.getPid());
        params.put("_input_charset", config.getCharset());
        params.put(AlipayParam.NOTIFY_URL.value(), RequestUrlUtils.getNotifyUrl(context));
        params.put(AlipayParam.RETURN_URL.value(), RequestUrlUtils.getReturnUrl(context));
        params.put("show_url", context.getQuitUrl());
        params.put("seller_id", config.getPid());
        params.put("payment_type", "1");
        params.put(AlipayParam.TRADE_NO.value(), context.getRecordId());
        params.put("total_fee", PayUtils.getCentMoney(context.getOrder().getAmount()));
        params.put(AlipayParam.SUBJECT.value(), context.getOrder().getSubject());
        params.put(AlipayParam.BODY.value(), context.getOrder().getBody());
        params.put("it_b_pay", config.getTimeout());
        params.put("app_pay", "N");

        String sign = AlipaySecurity.instance.sign(config, params);
        params.put(AlipayParam.SIGN_TYPE.value(), config.getSignType());
        params.put(AlipayParam.SIGN.value(), sign);
        return params;
    }
}