package com.cicada.component.bdpay.support;

import com.cicada.component.bdpay.bean.BdpayConfig;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.requester.AbstractRequester;
import com.cicada.utils.PayUtils;
import com.dotin.common.net.http.HttpClient;
import com.dotin.common.net.http.Response;
import com.dotin.common.utils.XmlUtils;

import java.util.Map;

/**
 * 京东支付-网络请求执行器
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public class BdpayRequester extends AbstractRequester<Object> {

    private final String getaway;

    public BdpayRequester(String getaway) {
        this.getaway = getaway;
    }

    @Override
    public Response execute(TradingContext context, Map<String, Object> params) {
        String msg = PayUtils.getQueryStr(params);
        //请求报文
        context.getMessage().setAgentRequestData(msg);
        Response rsp = doRequest(getaway + "?" + msg, null, HttpClient.MethodType.GET);
        return rsp;
    }

    @Override
    public boolean verify(TradingContext context, Object rsp) {
        return BdpaySecurity.instance.verify((BdpayConfig) context.getConfig(), ((Map) rsp));
    }


    @Override
    public Object parse(TradingContext context, Response rsp) {
        String content = rsp.getBody();
        context.getMessage().setAgentResponseData(content);//渠道返回数据
        Map<String, Object> params = XmlUtils.toMap(content, xmlConfig);
        return params;
    }

}