package com.cicada.component.wxpay.support;

import com.cicada.component.wxpay.bean.WxpayConfig;
import com.cicada.component.wxpay.enums.WxpayParam;
import com.cicada.component.wxpay.enums.WxpaySignType;
import com.cicada.core.Security;
import com.cicada.utils.PayUtils;
import com.cicada.utils.SecurityUtils;
import com.dotin.common.utils.StringUtils;

import java.util.Map;

/**
 * 微信-参数安全工具
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public class WxpaySecurity implements Security<WxpayConfig> {


    public final static WxpaySecurity instance = new WxpaySecurity();


    @Override
    public <T> String sign(WxpayConfig config, Map<String, T> params) {
        String signStr = StringUtils.concat(PayUtils.getQueryStr(params), "&key=", config.getMchKey());
        if (WxpaySignType.HMAC_SHA256.value().equals(params.get(WxpayParam.SIGN_TYPE.value()))) {
            return SecurityUtils.signHmacSha256(config.getMchKey(), signStr);
        }
        return SecurityUtils.signMd5(signStr);
    }

    @Override
    public <T> boolean verify(WxpayConfig config, Map<String, T> params) {
        String sign = (String) params.remove(WxpayParam.SIGN.value());
        if (StringUtils.isNotBlank(sign)) {
            String signStr = WxpaySecurity.instance.sign(config, params);
            params.put(WxpayParam.SIGN.value(), (T) sign);
            return StringUtils.isEquals(sign, signStr);
        }
        return true;
    }
}