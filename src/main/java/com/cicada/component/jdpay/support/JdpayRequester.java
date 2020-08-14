package com.cicada.component.jdpay.support;

import com.cicada.component.jdpay.bean.JdpayConfig;
import com.cicada.component.jdpay.emuns.JdpayParam;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.requester.AbstractRequester;
import com.dotin.common.net.http.HttpClient;
import com.dotin.common.net.http.Response;
import com.dotin.common.utils.StringUtils;
import com.dotin.common.utils.XmlUtils;

import java.util.Map;

/**
 * 京东支付-网络请求执行器
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public class JdpayRequester extends AbstractRequester<Object> {

    private final String getaway;


    private final static JdpayResultParser parser = new JdpayResultParser();


    public JdpayRequester(String getaway) {
        this.getaway = getaway;
    }

    @Override
    public Response execute(TradingContext context, Map<String, Object> params) {
        String msg = XmlUtils.toXml(params, JdpayParam.XML_ROOT.value(), xmlConfig);
        context.getMessage().setAgentRequestData(msg);//请求报文

        Response rsp = doRequest(getaway, StringUtils.getBytes(msg), HttpClient.ContentType.XML);
        return rsp;
    }

    @Override
    public boolean verify(TradingContext context, Object rsp) {
        if (rsp instanceof Map)
            return JdpaySecurity.instance.verify((JdpayConfig) context.getConfig(), ((Map) rsp));
        return true;
    }


    @Override
    public Object parse(TradingContext context, Response rsp) {
        if ("application/x-gzip".equals(rsp.getType())) {
            return rsp.getContent();
        }

        String content = rsp.getBody();
        context.getMessage().setAgentResponseData(content);//渠道返回数据
        Map<String, Object> params = XmlUtils.toMap(content, xmlConfig);
        String encrypt = (String) params.get(JdpayParam.ENCRYPT.value());
        if (StringUtils.isNotEmpty(encrypt)) {
            params.putAll(parser.parse(JdpaySecurity.instance.decrypt(encrypt,
                    ((JdpayConfig) context.getConfig()).getMchKey())));
        }

        return params;

    }

}