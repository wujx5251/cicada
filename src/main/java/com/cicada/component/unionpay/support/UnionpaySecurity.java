package com.cicada.component.unionpay.support;

import com.cicada.component.unionpay.bean.UnionpayConfig;
import com.cicada.component.unionpay.enums.UnionpayParam;
import com.cicada.component.unionpay.enums.UnionpaySignType;
import com.cicada.component.unionpay.enums.UnionpayVersion;
import com.cicada.core.Security;
import com.cicada.utils.PayUtils;
import com.cicada.utils.SecurityUtils;
import com.dotin.common.utils.StringUtils;
import com.dotin.common.utils.crypto.DigestUtils;

import java.util.Map;

/**
 * 银联-参数安全工具
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public class UnionpaySecurity implements Security<UnionpayConfig> {

    public final static UnionpaySecurity instance = new UnionpaySecurity();

    @Override
    public <T> String sign(UnionpayConfig config, Map<String, T> params) {
        String signStr = PayUtils.getQueryStr(params);
        String signType = (String) params.get(UnionpayParam.SIGN_TYPE.value());
        String version = (String) params.get("version");

        if (UnionpaySignType.SHA256.value().equals(signType)) {
            signStr = StringUtils.concat(signStr, "&", DigestUtils.sha256Hex(config.getSecureKey()));
            signStr = DigestUtils.sha256Hex(signStr);
        } else if (UnionpaySignType.SM3.value().equals(signType)) {
            signStr = StringUtils.concat(signStr, "&", DigestUtils.sm3Hex(config.getSecureKey()));
            signStr = DigestUtils.sm3Hex(signStr);
        } else if (UnionpayVersion.V5_1_0.value().equals(version)) {
            signStr = SecurityUtils.signSha256(config.getMchCert().getPrivateKey(), DigestUtils.sha256Hex(signStr));
        } else {
            signStr = SecurityUtils.signSha(config.getMchCert().getPrivateKey(), DigestUtils.sha1Hex(signStr));
        }
        return signStr;
    }

    @Override
    public <T> boolean verify(UnionpayConfig config, Map<String, T> params) {
        String signType = (String) params.get("signMethod");
        String sign = (String) params.remove("signature");
        String version = (String) params.get("version");

        String signStr = PayUtils.getQueryStr(params);
        params.put("signature", (T) sign);

        boolean verify;
        if (StringUtils.isBlank(sign)) {
            verify = false;
        } else if (UnionpaySignType.SHA256.value().equals(signType)) {
            signStr = StringUtils.concat(signStr, "&", DigestUtils.sha256Hex(config.getSecureKey()));
            verify = sign.equals(DigestUtils.sha256Hex(signStr));
        } else if (UnionpaySignType.SM3.value().equals(signType)) {
            signStr = StringUtils.concat(signStr, "&", DigestUtils.sm3Hex(config.getSecureKey()));
            verify = sign.equals(DigestUtils.sm3Hex(signStr));
        } else if (UnionpayVersion.V5_1_0.value().equals(version)) {
            verify = SecurityUtils.verifySha256ByCert(config.getPublicCert(), DigestUtils.sha256Hex(signStr), sign);
        } else {
            verify = SecurityUtils.verifyShaByCert(config.getPublicCert(), DigestUtils.sha1Hex(signStr), sign);
        }
        return verify;
    }
}