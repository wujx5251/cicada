package com.cicada.component.kqpay.support;

import com.cicada.component.kqpay.bean.KqpayConfig;
import com.cicada.core.Security;
import com.cicada.utils.PayUtils;
import com.cicada.utils.SecurityUtils;

import java.util.Map;

/**
 * 快钱-参数安全工具
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public class KqpaySecurity implements Security<KqpayConfig> {

    public final static KqpaySecurity instance = new KqpaySecurity();

    @Override
    public <T> String sign(KqpayConfig config, Map<String, T> params) {
        String signStr = PayUtils.getQueryStr(params, false, false);
        signStr = SecurityUtils.signSha(config.getMchCert().getPrivateKey(), signStr);
        return signStr;
    }

    @Override
    public <T> boolean verify(KqpayConfig config, Map<String, T> params) {
        String sign = (String) params.remove("signMsg");
        String signStr = PayUtils.getQueryStr(params);
        params.put("signMsg", (T) sign);

        boolean verify = SecurityUtils.verifyShaByCert(config.getPublicCert(), signStr, sign);
        return verify;
    }

    public boolean verify(KqpayConfig config, String tr3Xml) {
        int beginIndex = tr3Xml.indexOf("<signature>");
        int endIndex = tr3Xml.indexOf("</signature>");
        String sign = tr3Xml.substring(beginIndex + 11, endIndex);
        String signStr = tr3Xml.replaceAll("<signature>.*</signature>", "");

        boolean verify = SecurityUtils.verifyShaByCert(config.getPublicCert(), signStr, sign);
        return verify;
    }
}