package com.cicada.component.jdpay.support;

import com.cicada.component.jdpay.bean.JdpayConfig;
import com.cicada.component.jdpay.emuns.JdpayParam;
import com.cicada.core.Security;
import com.cicada.core.enums.Valuable;
import com.cicada.utils.PayUtils;
import com.cicada.utils.SecurityUtils;
import com.dotin.common.base.Charsets;
import com.dotin.common.enums.EncryptAlgoEnum;
import com.dotin.common.enums.EncryptModeEnum;
import com.dotin.common.enums.PaddingEnum;
import com.dotin.common.utils.Base64Utils;
import com.dotin.common.utils.HexUtils;
import com.dotin.common.utils.ObjectUtils;
import com.dotin.common.utils.StringUtils;
import com.dotin.common.utils.crypto.DigestUtils;

import java.util.Map;

/**
 * 京东支付-参数安全工具
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public class JdpaySecurity implements Security<JdpayConfig> {

    private final static String[] IGNORE = {JdpayParam.VERSION.value(), JdpayParam.MCH_ID.value(), JdpayParam.SIGN.value()};

    public final static JdpaySecurity instance = new JdpaySecurity();


    public Map<String, Object> encryptKv(JdpayConfig config, Map<String, Object> params) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            Object v = entry.getValue();
            String value = StringUtils.parse(v instanceof Valuable ? ((Valuable) v).value() : v);
            //过滤非加密字符
            if (StringUtils.isEmpty(value) || ObjectUtils.contains(entry.getKey(), IGNORE)) {
                continue;
            }

            entry.setValue(HexUtils.toHexStr(encrypt(value, config.getMchKey())));
        }
        return params;
    }

    public String encryptXml(JdpayConfig config, String data) {
        return Base64Utils.encode(HexUtils.toHex(encrypt(data, config.getMchKey())));
    }

    public void decrypt(JdpayConfig config, Map<String, Object> params) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            Object v = entry.getValue();
            String value = StringUtils.parse(v instanceof Valuable ? ((Valuable) v).value() : v);
            //过滤非加密字符
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            entry.setValue(decrypt(value, config.getMchKey()));
        }
    }

    public String signXml(JdpayConfig config, String signStr) {
        signStr = DigestUtils.sha256Hex(signStr);
        return SecurityUtils.encryptData(config.getPrivateKey(), signStr);
    }

    @Override
    public <T> String sign(JdpayConfig config, Map<String, T> params) {
        String signStr = PayUtils.getQueryStr(params);
        signStr = DigestUtils.sha256Hex(signStr);
        return SecurityUtils.encryptData(config.getPrivateKey(), signStr);
    }

    @Override
    public <T> boolean verify(JdpayConfig config, Map<String, T> params) {
        String sign = (String) params.get(JdpayParam.SIGN.value());
        if (StringUtils.isNotBlank(sign)) {
            sign = SecurityUtils.decryptData(config.getPublicKey(), sign);
            String signStr = DigestUtils.sha256Hex((String) params.get(JdpayParam.RESPONSE.value()));

            return StringUtils.isEquals(sign, signStr);
        }
        return false;
    }

    private byte[] encrypt(String data, String key) {
        return com.dotin.common.utils.crypto.SecurityUtils.encrypt(full(data.getBytes(Charsets.UTF_8)),
                Base64Utils.decode(key),
                com.dotin.common.utils.crypto.SecurityUtils.Config.custom().
                        setAlgorithm(EncryptAlgoEnum.DES3.DES3).
                        setEncrypt(EncryptModeEnum.ECB).
                        setPadding(PaddingEnum.NO_PADDING).build());
    }

    public String decrypt(String data, String key) {
        return toStr(com.dotin.common.utils.crypto.SecurityUtils.decrypt(HexUtils.toBytes(Base64Utils.decodeStr(data)),
                Base64Utils.decode(key),
                com.dotin.common.utils.crypto.SecurityUtils.Config.custom().
                        setAlgorithm(EncryptAlgoEnum.DES3).
                        setEncrypt(EncryptModeEnum.ECB).
                        setPadding(PaddingEnum.NO_PADDING).build())
        );
    }

    private String toStr(byte[] source) {
        byte[] tempData = new byte[HexUtils.toInt(source, false)];
        for (int i = 0; i < tempData.length; i++) {
            tempData[i] = source[i + 4];
        }
        return StringUtils.toString(tempData);
    }


    private byte[] full(byte[] source) {
        int merchantData = source.length;
        int x = (merchantData + 4) % 8;
        int y = x == 0 ? 0 : 8 - x;
        byte[] sizeByte = HexUtils.toBytes(merchantData, false);
        byte[] resultByte = new byte[merchantData + 4 + y];
        resultByte[0] = sizeByte[0];
        resultByte[1] = sizeByte[1];
        resultByte[2] = sizeByte[2];
        resultByte[3] = sizeByte[3];
        for (int i = 0; i < merchantData; i++) {
            resultByte[(4 + i)] = source[i];
        }
        for (int i = 0; i < y; i++) {
            resultByte[(merchantData + 4 + i)] = 0;
        }
        return resultByte;
    }


}