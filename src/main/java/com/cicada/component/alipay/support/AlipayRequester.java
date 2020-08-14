package com.cicada.component.alipay.support;

import com.alibaba.fastjson.JSON;
import com.cicada.component.alipay.bean.AlipayConfig;
import com.cicada.component.alipay.enums.AlipayParam;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.requester.AbstractRequester;
import com.cicada.utils.PayUtils;
import com.dotin.common.net.http.Response;
import com.dotin.common.utils.StringUtils;

import java.util.Map;

/**
 * 支付宝-网络请求执行器
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public class AlipayRequester extends AbstractRequester<Map<String, Object>> {

    private final String getaway;

    public AlipayRequester(String getaway) {
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
    public boolean verify(TradingContext context, Map<String, Object> rsp) {
        return AlipaySecurity.instance.verify((AlipayConfig) context.getConfig(), rsp);
    }


    @Override
    public Map<String, Object> parse(TradingContext context, Response rsp) {
        String content = rsp.getBody();
        context.getMessage().setAgentResponseData(content);//渠道返回数据
        Map<String, Object> result = AlipayResultParser.instance.parse(content);

        if (null != result) {
            Map<String, Object> data = JSON.parseObject((String) result.get(AlipayParam.RESPONSE.value()), Map.class);
            if (null != data){
                result.putAll(data);
            }
        }

        return result;
    }


}