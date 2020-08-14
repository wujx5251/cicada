package com.cicada.component.jdpay;

import com.cicada.component.jdpay.bean.JdpayConfig;
import com.cicada.component.jdpay.emuns.JdpayParam;
import com.cicada.component.jdpay.support.JdpayRequester;
import com.cicada.component.jdpay.support.JdpaySecurity;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.requester.RequestExecutor;
import com.cicada.utils.RequestUrlUtils;
import com.dotin.common.utils.DateUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 微信
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public abstract class JdpayAbstractPay extends JdpayAbstractNotify {

    protected String getTradeType() {
        return null;
    }

    protected String getAction() {
        return "";
    }

    @Override
    public Map<String, ?> build(TradingContext context) {
        JdpayConfig config = context.getConfig();
        Map<String, Object> params = new LinkedHashMap<String, Object>(15);
        params.put(JdpayParam.VERSION.value(), config.getVersion());
        params.put(JdpayParam.MCH_ID.value(), config.getMchId());
        params.put(JdpayParam.TRADE_NO.value(), context.getRecordId());
        params.put(JdpayParam.TRADE_TIME.value(), DateUtils.format(context.getCreateTime(), DateUtils.DATE_TIME_SHORT_FORMAT));
        params.put(JdpayParam.AMOUNT.value(), context.getOrder().getAmount());
        params.put(JdpayParam.CURRENCY.value(), context.getOrder().getCurrency());
        params.put(JdpayParam.SUBJECT.value(), context.getOrder().getSubject());
        params.put(JdpayParam.BODY.value(), context.getOrder().getBody());
        params.put(JdpayParam.TRADE_TYPE.value(), getTradeType());
        params.put(JdpayParam.USER_ID.value(), config.getUserId());
        params.put(JdpayParam.EXPIRE.value(), config.getTimeout());
        params.put(JdpayParam.NOTIFY_URL.value(), RequestUrlUtils.getNotifyUrl(context));
        params.put(JdpayParam.ORDER_TYPE.value(), "1");//0：实物，1：虚拟
        Map<String, Object> ext = getParams(context);
        if (null != ext)
            params.putAll(ext);

        String sign = sign(config, params);
        params.put(JdpayParam.SIGN.value(), sign);


        return encrypt(config, params);
    }

    protected Map<String, Object> encrypt(JdpayConfig config, Map<String, Object> params) {
        return JdpaySecurity.instance.encryptKv(config, params);
    }

    protected String sign(JdpayConfig config, Map<String, Object> params) {
        return JdpaySecurity.instance.sign(config, params);
    }

    protected Map<String, Object> getParams(TradingContext context) {
        return null;
    }

    @Override
    public RequestExecutor getRequestExecutor(TradingContext context) {
        JdpayConfig config = context.getConfig();
        return new JdpayRequester(config.getApiUrl() + getAction());
    }
}