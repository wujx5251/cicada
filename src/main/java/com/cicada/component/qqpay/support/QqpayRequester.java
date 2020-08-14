package com.cicada.component.qqpay.support;

import com.cicada.component.qqpay.bean.QqResponse;
import com.cicada.component.qqpay.bean.QqpayConfig;
import com.cicada.component.qqpay.enums.QqpayParam;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.requester.AbstractRequester;
import com.dotin.common.net.http.HttpClient;
import com.dotin.common.net.http.Response;
import com.dotin.common.utils.StringUtils;
import com.dotin.common.utils.XmlUtils;

import java.security.KeyStore;
import java.util.Map;

/**
 * qq-网络请求执行器
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public class QqpayRequester extends AbstractRequester<Object> {

    private final String getaway;
    private final boolean ssl;

    public QqpayRequester(String getaway) {
        this(getaway, false);
    }

    public QqpayRequester(String getaway, boolean ssl) {
        this.getaway = getaway;
        this.ssl = ssl;
    }

    @Override
    public Response execute(TradingContext context, Map<String, Object> params) {
        String msg = XmlUtils.toXml(params, QqpayParam.XML_ROOT.value(), xmlConfig);
        context.getMessage().setAgentRequestData(msg);//请求报文

        Response rsp;
        if (ssl) {
            QqpayConfig config = context.getConfig();
            KeyStore ks = config.getApiCert().getKs();
            rsp = doRequest(getaway, StringUtils.getBytes(msg), HttpClient.ContentType.XML, ks, config.getMchId());
        } else {
            rsp = doRequest(getaway, StringUtils.getBytes(msg), HttpClient.ContentType.XML);
        }
        return rsp;
    }

    @Override
    public boolean verify(TradingContext context, Object rsp) {
        if (rsp instanceof Map && QqResponse.SUCCESS.equals(((Map) rsp).get(QqResponse.CODE))) {
            return QqpaySecurity.instance.verify((QqpayConfig) context.getConfig(), ((Map) rsp));
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