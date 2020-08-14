package com.cicada.component.alipay;

import com.alibaba.fastjson.JSONObject;
import com.cicada.component.alipay.bean.AlipayConfig;
import com.cicada.component.alipay.enums.AlipayParam;
import com.cicada.component.alipay.support.AlipaySecurity;
import com.cicada.core.bean.TradingContext;
import com.cicada.utils.PayUtils;
import com.cicada.utils.RequestUrlUtils;
import com.dotin.common.utils.DateUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public abstract class AlipayAbstractPay extends AlipayAbstractNotify {

    abstract protected String getMethod();

    @Override
    public Map<String, ?> build(TradingContext context) {
        AlipayConfig config = context.getConfig();
        Map<String, String> params = new HashMap<String, String>(15);
        params.put(AlipayParam.APP_ID.value(), config.getAppId());
        params.put(AlipayParam.METHOD.value(), getMethod());
        params.put(AlipayParam.FORMAT.value(), config.getFormat());
        params.put(AlipayParam.CHARSET.value(), config.getCharset());
        params.put(AlipayParam.VERSION.value(), config.getVersion());
        params.put(AlipayParam.TIMESTAMP.value(), DateUtils.format(context.getCreateTime()));
        params.put(AlipayParam.SIGN_TYPE.value(), config.getSignType());

        params.put(AlipayParam.NOTIFY_URL.value(), RequestUrlUtils.getNotifyUrl(context));
        params.put(AlipayParam.RETURN_URL.value(), RequestUrlUtils.getReturnUrl(context));

        String bizStr = getBizContent(context).toJSONString();
        params.put(AlipayParam.CONTENT.value(), bizStr);

        String sign = AlipaySecurity.instance.sign(config, params);
        params.put(AlipayParam.SIGN.value(), sign);
        return params;
    }

    protected JSONObject getBizContent(TradingContext context) {
        JSONObject params = new JSONObject();
        AlipayConfig config = context.getConfig();
        params.put(AlipayParam.TRADE_NO.value(), context.getRecordId());
        params.put(AlipayParam.AMOUNT.value(), PayUtils.getCentMoney(context.getOrder().getAmount()));
        params.put(AlipayParam.SUBJECT.value(), context.getOrder().getSubject());
        params.put(AlipayParam.BODY.value(), context.getOrder().getBody());
        params.put(AlipayParam.TIMEOUT.value(), config.getTimeout());
        return params;
    }

}