package com.cicada.component.jdpay.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.cicada.Constants;
import com.cicada.core.bean.AccountConfig;
import com.cicada.core.serializer.KeySerializer;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

/**
 * 京东支付配置参数
 *
 * @author: WUJXIAO
 * @create: 2019-01-25 08:58
 * @version: 1.0
 */
public class JdpayConfig implements AccountConfig {
    /**
     * 商户appid
     */
    private String mchId;

    /**
     * 商户私钥
     */
    private String mchKey;

    /**
     * 用户账号
     */
    private String userId;

    /**
     * 私钥 pkcs8格式的
     */
    @JSONField(deserializeUsing = KeySerializer.class, serializeUsing = KeySerializer.class)
    private PrivateKey privateKey;

    /**
     * 公钥 pkcs8格式的
     */
    @JSONField(deserializeUsing = KeySerializer.class, serializeUsing = KeySerializer.class)
    private PublicKey publicKey;

    // 请求网关地址
    private String gatewayUrl = "https://wepay.jd.com/jdpay/saveOrder";
    /* PC版调用地址：https://wepay.jd.com/jdpay/saveOrder
        H5版调用地址：https://h5pay.jd.com/jdpay/saveOrder*/

    //接口网关地址
    private String apiUrl = "https://paygate.jd.com";


    /**
     * 订单有效期 单位毫秒
     */
    private int timeout = Constants.ORDER_ACTIVE_TIME;

    /**
     * 是否重试
     */
    private boolean retry;

    /**
     * 版本
     */
    private String version = "V2.0";

    /**
     * 初始化支付宝配置
     *
     * @param configParam
     * @return
     */
    public static JdpayConfig build(String configParam) {
        JdpayConfig config = JSON.parseObject(configParam, JdpayConfig.class, jsonConfig);
        config.timeout *= 60;
        return config;
    }

    @Override
    public String getMchId() {
        return mchId;
    }

    @Override
    public String getAppId() {
        return null;
    }

    public String getMchKey() {
        return mchKey;
    }

    public String getUserId() {
        return userId;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public String getVersion() {
        return version;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public int getTimeout() {
        return timeout;
    }

    @Override
    public boolean isRetry() {
        return retry;
    }

    @Override
    public Date getLastTime() {
        return new Date(System.currentTimeMillis() + timeout);
    }
}