package com.cicada.component.qqpay.support;

import com.cicada.component.qqpay.bean.QqpayConfig;
import com.cicada.component.qqpay.enums.QqpayParam;
import com.cicada.component.qqpay.enums.QqpaySignType;
import com.cicada.core.Security;
import com.cicada.utils.PayUtils;
import com.cicada.utils.SecurityUtils;
import com.dotin.common.utils.StringUtils;

import java.util.Map;

/**
 * qq-参数安全工具
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public class QqpaySecurity implements Security<QqpayConfig> {


    public final static QqpaySecurity instance = new QqpaySecurity();


    @Override
    public <T> String sign(QqpayConfig config, Map<String, T> params) {
        String signStr = StringUtils.concat(PayUtils.getQueryStr(params), "&key=", config.getMchKey());
        if (QqpaySignType.HMAC_SHA256.value().equals(params.get(QqpayParam.SIGN_TYPE.value()))) {
            return SecurityUtils.signHmacSha256(config.getMchKey(), signStr);
        }
        return SecurityUtils.signMd5(signStr);
    }

    @Override
    public <T> boolean verify(QqpayConfig config, Map<String, T> params) {
        String sign = (String) params.remove(QqpayParam.SIGN.value());
        if (StringUtils.isNotBlank(sign)) {
            String signStr = QqpaySecurity.instance.sign(config, params);
            params.put(QqpayParam.SIGN.value(), (T) sign);
            return StringUtils.isEquals(sign, signStr);
        }
        return true;
    }
}