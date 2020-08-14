package com.cicada.component.unionpay.support;

import com.cicada.component.unionpay.bean.UnionpayConfig;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.requester.AbstractRequester;
import com.cicada.utils.PayUtils;
import com.dotin.common.net.http.Response;
import com.dotin.common.utils.StringUtils;

import java.util.Map;

/**
 * 银联-网络请求执行器
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public class UnionpayRequester extends AbstractRequester<Map<String, ?>> {

    private final String getaway;

    public UnionpayRequester(String getaway) {
        this.getaway = getaway;
    }

    @Override
    public Response execute(TradingContext context, Map<String, Object> params) {
        String msg = PayUtils.getQueryStr(params, true);
        context.getMessage().setAgentRequestData(msg);//请求报文

        Response rsp = doRequest(getaway, StringUtils.getBytes(msg));

        return rsp;
    }

    @Override
    public boolean verify(TradingContext context, Map<String, ?> rsp) {
        return UnionpaySecurity.instance.verify((UnionpayConfig) context.getConfig(), rsp);
    }


    @Override
    public Map<String, ?> parse(TradingContext context, Response rsp) {
        String content = rsp.getBody();
        context.getMessage().setAgentResponseData(content);//渠道返回数据
        return UnionpayResultParser.instance.parse(content);
    }

}