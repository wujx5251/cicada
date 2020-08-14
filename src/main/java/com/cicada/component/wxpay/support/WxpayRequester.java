package com.cicada.component.wxpay.support;

import com.cicada.component.wxpay.bean.WxResponse;
import com.cicada.component.wxpay.bean.WxpayConfig;
import com.cicada.component.wxpay.enums.WxpayParam;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.requester.AbstractRequester;
import com.dotin.common.net.http.HttpClient;
import com.dotin.common.net.http.Response;
import com.dotin.common.utils.StringUtils;
import com.dotin.common.utils.XmlUtils;

import java.security.KeyStore;
import java.util.Map;

/**
 * 微信-网络请求执行器
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public class WxpayRequester extends AbstractRequester<Object> {

    private final String getaway;
    private final boolean ssl;

    public WxpayRequester(String getaway) {
        this(getaway, false);
    }

    public WxpayRequester(String getaway, boolean ssl) {
        this.getaway = getaway;
        this.ssl = ssl;
    }

    @Override
    public Response execute(TradingContext context, Map<String, Object> params) {
        String msg = XmlUtils.toXml(params, WxpayParam.XML_ROOT.value(), xmlConfig);
        context.getMessage().setAgentRequestData(msg);//请求报文

        Response rsp;
        if (ssl) {
            WxpayConfig config = context.getConfig();
            KeyStore ks = config.getApiCert().getKs();
            rsp = doRequest(getaway, StringUtils.getBytes(msg), HttpClient.ContentType.XML, ks, config.getMchId());
        } else {
            rsp = doRequest(getaway, StringUtils.getBytes(msg), HttpClient.ContentType.XML);
        }
        return rsp;
    }

    @Override
    public boolean verify(TradingContext context, Object rsp) {
        if (rsp instanceof Map && WxResponse.SUCCESS.equals(((Map) rsp).get(WxResponse.CODE))) {
            return WxpaySecurity.instance.verify((WxpayConfig) context.getConfig(), ((Map) rsp));
        }
        return true;
    }


    @Override
    public Object parse(TradingContext context, Response rsp) {
        if ("application/x-gzip".equals(rsp.getType())) {
            return rsp.getContent();
        }
        String content = rsp.getBody();
        context.getMessage().setAgentResponseData(content);//渠道返回数据
        return XmlUtils.toMap(content, xmlConfig);

    }

}