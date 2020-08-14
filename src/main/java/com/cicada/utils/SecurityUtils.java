package com.cicada.utils;

import com.dotin.common.base.Charsets;
import com.dotin.common.enums.EncryptAlgoEnum;
import com.dotin.common.enums.EncryptModeEnum;
import com.dotin.common.enums.PaddingEnum;
import com.dotin.common.utils.Base64Utils;
import com.dotin.common.utils.StringUtils;
import com.dotin.common.utils.crypto.Cert;
import com.dotin.common.utils.crypto.DigestUtils;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 安全工具类
 * Created by jinxiao.wu
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * RSA2 参数签名
     *
     * @param key
     * @param content
     * @return
     */
    public static String signSha256(PrivateKey key, String content) {
        return signSha256(key, StringUtils.getBytes(content));
    }

    /**
     * RSA2 参数签名
     *
     * @param key
     * @param content
     * @return
     */
    public static String signSha256(PrivateKey key, byte[] content) {
        byte[] sign = DigestUtils.signSha256(key, content);
        return Base64Utils.encode(sign);
    }

    /**
     * RSA2 验签签名
     *
     * @param cert
     * @param content
     * @return
     */
    public static boolean verifySha256ByCert(Cert cert, String content, String sign) {
        return verifySha256ByCert(cert, StringUtils.getBytes(content), sign);
    }

    /**
     * RSA2 验签签名
     *
     * @param cert
     * @param content
     * @return
     */
    public static boolean verifySha256ByCert(Cert cert, byte[] content, String sign) {
        boolean v = verifySha256(cert.getPublicKey(), content, sign);
        return v;
    }

    /**
     * RSA2 验签签名
     *
     * @param key
     * @param content
     * @return
     */
    public static boolean verifySha256(PublicKey key, String content, String sign) {
        return verifySha256(key, StringUtils.getBytes(content), sign);
    }

    /**
     * RSA2 验签签名
     *
     * @param key
     * @param content
     * @return
     */
    public static boolean verifySha256(PublicKey key, byte[] content, String sign) {
        boolean v = DigestUtils.verifySha256(key, content, Base64Utils.decode(sign));
        return v;
    }


    /**
     * RSA 参数签名
     *
     * @param key
     * @param content
     * @return
     */
    public static String signSha(PrivateKey key, String content) {
        return signSha(key, StringUtils.getBytes(content));
    }

    /**
     * RSA 参数签名
     *
     * @param key
     * @param content
     * @return
     */
    public static String signSha(PrivateKey key, byte[] content) {
        byte[] sign = DigestUtils.signSha1(key, content);
        return Base64Utils.encode(sign);
    }

    /**
     * RSA 验签签名
     *
     * @param key
     * @param content
     * @return
     */
    public static boolean verifySha(PublicKey key, String content, String sign) {
        return verifySha(key, StringUtils.getBytes(content), sign);
    }

    /**
     * RSA 验签签名
     *
     * @param key
     * @param content
     * @return
     */
    public static boolean verifySha(PublicKey key, byte[] content, String sign) {
        boolean v = DigestUtils.verifySha1(key, content, Base64Utils.decode(sign));
        return v;
    }

    /**
     * RSA 验签签名
     *
     * @param cert
     * @param content
     * @return
     */
    public static boolean verifyShaByCert(Cert cert, String content, String sign) {
        return verifyShaByCert(cert, StringUtils.getBytes(content), sign);
    }

    /**
     * RSA 验签签名
     *
     * @param cert
     * @param content
     * @return
     */
    public static boolean verifyShaByCert(Cert cert, byte[] content, String sign) {
        boolean v = DigestUtils.verifySha1(cert.getPublicKey(),
                content, Base64Utils.decode(sign));
        return v;
    }

    /**
     * md5 参数签名
     *
     * @param content
     * @return
     */
    public static String signMd5sm(String content) {
        String sign = DigestUtils.md5Hex(content.getBytes(Charsets.UTF_8));
        return sign;
    }

    /**
     * md5 参数签名
     *
     * @param content
     * @return
     */
    public static String signMd5(String content) {
        return signMd5sm(content).toUpperCase();
    }

    /**
     * HMacSha256 参数签名
     *
     * @param key
     * @param content
     * @return
     */
    public static String signHmacSha256(String key, String content) {
        String sign = DigestUtils.hmacHex(DigestUtils.getMacSha256(), key, content);
        return sign.toUpperCase();
    }

    /**
     * 加密数据
     *
     * @param key
     * @param data
     * @return
     */
    public static String encryptData(Key key, String data) {
        if (StringUtils.isEmpty(data))
            return null;

        byte[] encrypt = com.dotin.common.utils.crypto.SecurityUtils.encrypt(StringUtils.getBytes(data),
                key,
                com.dotin.common.utils.crypto.SecurityUtils.Config.custom().
                        setAlgorithm(EncryptAlgoEnum.RSA).
                        setEncrypt(EncryptModeEnum.ECB).
                        setPadding(PaddingEnum.PKCS1).build());
        return Base64Utils.encode(encrypt);
    }

    /**
     * 解密数据
     *
     * @param key
     * @param data
     * @return
     */
    public static String decryptData(Key key, String data) {
        if (StringUtils.isEmpty(data))
            return null;

        byte[] encrypt = com.dotin.common.utils.crypto.SecurityUtils.decrypt(Base64Utils.decode(data),
                key,
                com.dotin.common.utils.crypto.SecurityUtils.Config.custom().
                        setAlgorithm(EncryptAlgoEnum.RSA).
                        setEncrypt(EncryptModeEnum.ECB).
                        setPadding(PaddingEnum.PKCS1).build());
        return StringUtils.toString(encrypt);
    }


}