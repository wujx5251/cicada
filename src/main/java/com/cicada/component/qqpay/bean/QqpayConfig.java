package com.cicada.component.qqpay.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.cicada.Constants;
import com.cicada.core.bean.AccountConfig;
import com.cicada.core.serializer.CertSerializer;
import com.dotin.common.utils.crypto.Cert;

import java.util.Date;

/**
 * qq配置参数
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public class QqpayConfig implements AccountConfig {
    /**
     * 商户appid
     */
    private String mchId;
    /**
     * 商户密钥.
     */
    private String mchKey;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * api证书base64,退款使用
     */
    @JSONField(deserializeUsing = CertSerializer.class, serializeUsing = CertSerializer.class, ordinal = 99)
    private Cert apiCert;

    // 请求网关地址
    private String gatewayUrl = "https://qpay.qq.com/";
    //private String gatewayUrl = "https://api.mch.weixin.qq.com/sandboxnew";

    /**
     * 订单有效期 单位毫秒
     */
    private int timeout = Constants.ORDER_ACTIVE_TIME;

    /**
     * 签名方式 MD5,HMAC-SHA256
     */
    private String signType = "MD5";

    /**
     * 是否重试
     */
    private boolean retry;

    /**
     * 初始化支付宝配置
     *
     * @param configParam
     * @return
     */
    public static QqpayConfig build(String configParam) {
        QqpayConfig config = JSON.parseObject(configParam, QqpayConfig.class, jsonConfig);
        config.timeout *= (60 * 1000);
        return config;
    }

    @Override
    public String getMchId() {
        return mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    @Override
    public String getAppId() {
        return appId;
    }

    public Cert getApiCert() {
        return apiCert;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getSignType() {
        return signType;
    }

    @Override
    public boolean isRetry() {
        return retry;
    }

    @Override
    public Date getLastTime() {
        return new Date(System.currentTimeMillis() + timeout);
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }
}