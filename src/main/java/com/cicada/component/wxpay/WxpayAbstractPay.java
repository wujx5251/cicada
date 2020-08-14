package com.cicada.component.wxpay;

import com.cicada.component.wxpay.bean.WxResponse;
import com.cicada.component.wxpay.bean.WxpayConfig;
import com.cicada.component.wxpay.enums.WxpayAction;
import com.cicada.component.wxpay.enums.WxpayParam;
import com.cicada.component.wxpay.enums.WxpaySignType;
import com.cicada.component.wxpay.interfaces.WxpaySandbox;
import com.cicada.component.wxpay.support.WxpayRequester;
import com.cicada.component.wxpay.support.WxpaySecurity;
import com.cicada.core.Pay;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.TradingStatus;
import com.cicada.core.generate.Generator;
import com.cicada.core.requester.RequestExecutor;
import com.cicada.utils.RequestUrlUtils;
import com.dotin.common.utils.DateUtils;
import com.dotin.common.utils.LocalUtil;
import com.dotin.spring.holder.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public abstract class WxpayAbstractPay extends WxpayAbstractNotify {

    protected String getTradeType() {
        return null;
    }

    protected String getAction() {
        return WxpayAction.CREATE_ORDER.value();
    }

    @Autowired
    private Generator generator;

    @Override
    public TradingResult doPay(TradingContext context) {
        WxpayConfig config = context.getConfig();
        if (!this.getClass().equals(WxpaySandbox.class) &&
                config.getGatewayUrl().endsWith("sandboxnew")) {//微信沙箱环境
            Pay pay = SpringContextHolder.getBean("wxpaySandbox");
            TradingResult<WxResponse> result = pay.doPay(context);
            config.setSignType(WxpaySignType.MD5.value());

            if (TradingStatus.SUCCESS.equals(result.getStatus()))
                config.setMchKey(result.getData().getSandboxSignkey());
        }
        return super.doPay(context);
    }

    @Override
    public Map<String, ?> build(TradingContext context) {
        WxpayConfig config = context.getConfig();
        Map<String, Object> params = new HashMap<String, Object>(16);
        params.put(WxpayParam.APP_ID.value(), config.getAppId());
        params.put(WxpayParam.MCH_ID.value(), config.getMchId());
        params.put(WxpayParam.TRADE_TYPE.value(), getTradeType());
        params.put(WxpayParam.NONCE_STR.value(), generator.generateSalt(16));
        params.put(WxpayParam.SIGN_TYPE.value(), config.getSignType());
        Map<String, Object> ext = getParams(context);
        if (null != ext)
            params.putAll(ext);

        String sign = WxpaySecurity.instance.sign(config, params);
        params.put(WxpayParam.SIGN.value(), sign);
        return params;
    }

    protected Map<String, Object> getParams(TradingContext context) {
        WxpayConfig config = context.getConfig();
        Map<String, Object> params = new HashMap<String, Object>(6);
        params.put(WxpayParam.NOTIFY_URL.value(), RequestUrlUtils.getNotifyUrl(context));
        params.put(WxpayParam.CLIENT_IP.value(), LocalUtil.LOCAL_IP);
        params.put(WxpayParam.TRADE_NO.value(), context.getRecordId());
        params.put(WxpayParam.AMOUNT.value(), context.getOrder().getAmount());
        params.put(WxpayParam.CURRENCY.value(), context.getOrder().getCurrency());
        params.put(WxpayParam.SUBJECT.value(), context.getOrder().getSubject());
        params.put(WxpayParam.BODY.value(), context.getOrder().getBody());
        params.put(WxpayParam.STRAT_TIME.value(), DateUtils.format(context.getCreateTime(),
                DateUtils.DATE_TIME_SHORT_FORMAT));
        params.put(WxpayParam.TIMEOUT.value(),
                DateUtils.format(context.getCreateTime() + config.getTimeout(),
                        DateUtils.DATE_TIME_SHORT_FORMAT));
        return params;
    }

    @Override
    public RequestExecutor getRequestExecutor(TradingContext context) {
        WxpayConfig config = context.getConfig();
        return new WxpayRequester(config.getGatewayUrl() + getAction());
    }
}