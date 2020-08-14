package com.cicada.component.alipay.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.Feature;
import com.cicada.Constants;
import com.cicada.component.alipay.enums.AlipaySignType;
import com.cicada.core.bean.AccountConfig;
import com.cicada.core.serializer.KeySerializer;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

/**
 * 支付宝配置参数
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public class AlipayConfig implements AccountConfig {

    /**
     * 商户id
     */
    private String mchId;

    /**
     * 商户密钥
     */
    private String mchKey;

    /**
     * 合作者身份ID,旧版
     */
    private String pId;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 私钥 pkcs8格式的
     */
    @JSONField(deserializeUsing = KeySerializer.class, serializeUsing = KeySerializer.class)
    private PrivateKey privateKey;

    /**
     * 支付宝公钥
     */
    @JSONField(deserializeUsing = KeySerializer.class, serializeUsing = KeySerializer.class)
    private PublicKey publicKey;

    /**
     * 是否重试
     */
    private boolean retry;

    /**
     * 订单有效期
     */
    private int timeout = Constants.ORDER_ACTIVE_TIME;

    /**
     * 请求网关地址
     */
    private String gatewayUrl = "https://openapi.alipay.com/gateway.do";
    //private String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    /**
     * 签名方式 RSA,RSA2
     */
    private String signType = AlipaySignType.RSA2.value();

    /**
     * 编码
     */
    private String charset = "utf-8";

    /**
     * 返回格式
     */
    private String format = "json";

    /**
     * 版本
     */
    private String version = "1.0";


    /**
     * 初始化支付宝配置
     *
     * @param configParam
     * @return
     */
    public static AlipayConfig build(String configParam) {
        AlipayConfig config = JSON.parseObject(configParam, AlipayConfig.class, jsonConfig/*, Feature.SupportNonPublicField*/);
        return config;
    }

    @Override
    public String getMchId() {
        return mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    public String getPid() {
        return pId;
    }

    @Override
    public String getAppId() {
        return appId;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    @Override
    public boolean isRetry() {
        return retry;
    }

    @Override
    public Date getLastTime() {
        return new Date(System.currentTimeMillis() + timeout * 60 * 1000);
    }

    public String getTimeout() {
        return timeout + "m";
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public String getSignType() {
        return signType;
    }

    public String getCharset() {
        return charset;
    }

    public String getFormat() {
        return format;
    }

    public String getVersion() {
        return version;
    }
}