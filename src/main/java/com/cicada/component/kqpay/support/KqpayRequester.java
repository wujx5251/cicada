package com.cicada.component.kqpay.support;

import com.cicada.component.kqpay.bean.KqPartObject;
import com.cicada.component.kqpay.bean.KqpayConfig;
import com.cicada.component.kqpay.enums.KqpayParam;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Valuable;
import com.cicada.core.requester.AbstractRequester;
import com.dotin.common.net.http.HttpClient;
import com.dotin.common.net.http.Response;
import com.dotin.common.utils.StringUtils;
import com.dotin.common.utils.XmlUtils;

import java.security.KeyStore;
import java.util.Map;

/**
 * 快钱-网络请求执行器
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public class KqpayRequester extends AbstractRequester<Map<String, Object>> {

    private final String getaway;

    public KqpayRequester(String getaway) {
        this.getaway = getaway;
        xmlConfig.setNamespace("http://www.99bill.com/mas_cnp_merchant_interface").
                setHasVer(true).setEditor(valueEditor);
    }

    @Override
    public Response execute(TradingContext context, Map<String, Object> params) {
        String msg = XmlUtils.toXml(params, KqpayParam.XML_ROOT.value(), xmlConfig);
        context.getMessage().setAgentRequestData(msg);//请求报文
        KqpayConfig config = context.getConfig();
        KeyStore ks = config.getMchCert().getKs();

        Response rsp = doRequest(getaway, StringUtils.getBytes(msg), HttpClient.ContentType.XML, ks,
                config.getMchCertPwd(), config.getAuth());
        return rsp;
    }


    @Override
    public Map<String, Object> parse(TradingContext context, Response rsp) {
        String msg = rsp.getBody();
        context.getMessage().setAgentResponseData(msg);//渠道返回数据
        return XmlUtils.toMap(msg, xmlConfig);
    }


    private static final XmlUtils.ValueEditor valueEditor = new XmlUtils.ValueEditor() {
        @Override
        protected <T> T getValue(Object parent, Object obj, Object fieldName) {
            if (parent == null) {
                if (obj instanceof KqPartObject)
                    return (T) ((KqPartObject) obj).generate();
                if (obj instanceof Valuable)
                    return (T) ((Valuable) obj).value();
            } else if (parent instanceof Map && obj instanceof Map) {
                ((Map) parent).putAll((Map) obj);
                return null;
            }
            return (T) obj;
        }
    };
}