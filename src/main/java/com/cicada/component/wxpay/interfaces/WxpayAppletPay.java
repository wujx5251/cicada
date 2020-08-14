package com.cicada.component.wxpay.interfaces;

import com.alibaba.fastjson.JSON;
import com.cicada.core.annotation.Component;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.dotin.common.utils.StringUtils;

import java.util.Map;

/**
 * 微信 小程序 JSAPI
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.APPLET_PAY, channel = Channel.WXPAY, product = ProductType.APPLET)
public class WxpayAppletPay extends WxpayJsPay {

    @Override
    protected String generateJS(Map<String, Object> params, String url, String quitUrl) {
        StringBuilder sb = new StringBuilder();
        String pm = JSON.toJSONString(params);
        sb.append("<script type=\"text/javascript\">");
        sb.append("wx.requestPayment(");
        sb.append(pm.substring(0, pm.length() - 1));
        sb.append(",\"complete\":function(res){if (res.err_msg == \"requestPayment:ok\") {");
        sb.append("window.location.href = \"");
        sb.append(url);
        sb.append("\";} else {");
        sb.append("if (res.err_msg == \"requestPayment:fail\") alert(res.err_msg);");
        if (StringUtils.isNotEmpty(quitUrl)) {
            sb.append("window.location.href = \"");
            sb.append(quitUrl).append("\";");
        }
        sb.append("}}});</script>");
        return sb.toString();
    }
}