package com.cicada.component.wxpay.interfaces;

import com.alibaba.fastjson.JSON;
import com.cicada.component.wxpay.WxpayAbstractPay;
import com.cicada.component.wxpay.bean.WxResponse;
import com.cicada.component.wxpay.bean.WxpayConfig;
import com.cicada.component.wxpay.enums.WxpayParam;
import com.cicada.component.wxpay.enums.WxpayTradeType;
import com.cicada.component.wxpay.support.WxpaySecurity;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.enums.TradingStatus;
import com.cicada.utils.RequestUrlUtils;
import com.dotin.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信 统一支付 JSAPI
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.JS_PAY, channel = Channel.WXPAY, product = ProductType.JSAPI)
public class WxpayJsPay extends WxpayAbstractPay {

    @Override
    protected String getTradeType() {
        return WxpayTradeType.JSAPI.value();
    }

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = super.getParams(context);
        params.put(WxpayParam.OPEN_ID.value(), context.getExtra().getOpenId());
        return params;
    }

    @Override
    protected TradingResult render(TradingContext context, TradingResult result) {
        WxpayConfig config = context.getConfig();
        Map<String, Object> params = new HashMap<String, Object>(6);
        params.put(WxpayParam.APPID.value(), config.getAppId());
        params.put(WxpayParam.NONCESTR_JS.value(), ((WxResponse) result.getData()).getNonceStr());
        params.put(WxpayParam.PACKAGE.value(), "prepay_id=" + ((WxResponse) result.getData()).getPrepayId());
        params.put(WxpayParam.TIMESTAMP_JS.value(), StringUtils.parse(context.getCreateTime() / 1000));
        params.put(WxpayParam.SIGNTYPE.value(), config.getSignType());
        params.put(WxpayParam.PAYSIGN.value(), WxpaySecurity.instance.sign(config, params));


        String data = generateJS(params, RequestUrlUtils.getReturnUrl(context), context.getQuitUrl());
        context.getMessage().setPagePayData(data);

        return result.setStatus(TradingStatus.PENDING).
                setAmount(context.getOrder().getAmount()).
                setData(RequestUrlUtils.getPayUrl(context));
    }

    protected String generateJS(Map<String, Object> params, String url, String quitUrl) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script type=\"text/javascript\">function onBridgeReady() {");
        sb.append("WeixinJSBridge.invoke('getBrandWCPayRequest',");
        sb.append(JSON.toJSONString(params));
        sb.append(",function (res) {if (res.err_msg == \"get_brand_wcpay_request:ok\") {");
        sb.append("window.location.href = \"");
        sb.append(url);
        sb.append("\";} else {");
        sb.append("if (res.err_msg != \"get_brand_wcpay_request:cancel\") alert(res.err_msg);");
        if (StringUtils.isNotEmpty(quitUrl)) {
            sb.append("window.location.href = \"");
            sb.append(quitUrl).append("\";");
        }
        sb.append("}});}if (typeof WeixinJSBridge == \"undefined\") {");
        sb.append("if (document.addEventListener) {");
        sb.append("document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);");
        sb.append("} else if (document.attachEvent) {");
        sb.append("document.attachEvent('WeixinJSBridgeReady', onBridgeReady);");
        sb.append("document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);");
        sb.append("}} else {onBridgeReady();}</script>");

        return sb.toString();
    }
}