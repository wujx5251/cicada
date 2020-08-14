package com.cicada.component.bdpay.support;

import com.cicada.component.bdpay.bean.BdpayConfig;
import com.cicada.component.bdpay.emuns.BdpayParam;
import com.cicada.component.bdpay.emuns.BdpaySignType;
import com.cicada.core.Security;
import com.cicada.utils.PayUtils;
import com.dotin.common.base.Charsets;
import com.dotin.common.utils.StringUtils;
import com.dotin.common.utils.crypto.DigestUtils;

import java.util.Map;

/**
 * 百度钱包-参数安全工具
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public class BdpaySecurity implements Security<BdpayConfig> {

    public final static BdpaySecurity instance = new BdpaySecurity();

    @Override
    public <T> String sign(BdpayConfig config, Map<String, T> params) {
        return sign(config, params, true);
    }

    public <T> String sign(BdpayConfig config, Map<String, T> params, boolean filter) {
        String signStr = PayUtils.getQueryStr(params, Charsets.GBK, filter);
        signStr = StringUtils.concat(signStr, "&key=", config.getMchKey());
        byte[] sign = StringUtils.getBytes(signStr, Charsets.GBK);
        if (BdpaySignType.SHA1.value().equals(params.get(BdpayParam.SIGN_TYPE.value()))) {
            return DigestUtils.sha1Hex(sign);
        } else {
            return DigestUtils.md5Hex(sign);
        }
    }

    @Override
    public <T> boolean verify(BdpayConfig config, Map<String, T> params) {
        String sign = (String) params.remove(BdpayParam.SIGN.value());
        if (StringUtils.isNotBlank(sign)) {
            String signStr = sign(config, params, false);
            params.put(BdpayParam.SIGN.value(), (T) sign);
            return StringUtils.isEquals(sign, signStr);
        }
        return true;
    }

}