package com.cicada.component.alipay.support;

import com.cicada.component.alipay.bean.AlipayConfig;
import com.cicada.component.alipay.enums.AlipayParam;
import com.cicada.component.alipay.enums.AlipaySignType;
import com.cicada.core.Security;
import com.cicada.utils.PayUtils;
import com.cicada.utils.SecurityUtils;
import com.dotin.common.utils.StringUtils;

import java.util.Map;

/**
 * 支付宝-参数安全工具
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public class AlipaySecurity implements Security<AlipayConfig> {


    public final static AlipaySecurity instance = new AlipaySecurity();


    @Override
    public <T> String sign(AlipayConfig config, Map<String, T> params) {
        String signStr = PayUtils.getQueryStr(params);
        if (AlipaySignType.RSA2.value().equals(config.getSignType())) {
            return SecurityUtils.signSha256(config.getPrivateKey(), signStr);
        } else if (AlipaySignType.MD5.value().equals(config.getSignType())) {
            return SecurityUtils.signMd5sm(signStr + config.getMchKey());
        }
        return SecurityUtils.signSha(config.getPrivateKey(), signStr);
    }


    @Override
    public <T> boolean verify(AlipayConfig config, Map<String, T> params) {
        String sign = (String) params.remove(AlipayParam.SIGN.value());
        String signType = (String) params.remove(AlipayParam.SIGN_TYPE.value());
        String signStr = (String) params.remove(AlipayParam.RESPONSE.value());
        if (null == sign) return true;

        if (null == signStr)//异步校验
            signStr = PayUtils.getQueryStr(params);

        params.put(AlipayParam.SIGN.value(), (T) sign);
        params.put(AlipayParam.SIGN_TYPE.value(), (T) signType);
        if (AlipaySignType.RSA2.value().equals(config.getSignType())) {
            return SecurityUtils.verifySha256(config.getPublicKey(), signStr, sign);
        } else if (AlipaySignType.MD5.value().equals(config.getSignType())) {
            return StringUtils.isEquals(sign, SecurityUtils.signMd5sm(signStr + config.getMchKey()));
        }
        return SecurityUtils.verifySha(config.getPublicKey(), signStr, sign);
    }

}