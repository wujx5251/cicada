package com.cicada.component.bdpay.bean;

import com.alibaba.fastjson.JSON;
import com.cicada.Constants;
import com.cicada.component.bdpay.emuns.BdpaySignType;
import com.cicada.core.bean.AccountConfig;

import java.util.Date;

/**
 * 百度钱包配置参数
 *
 * @author: WUJXIAO
 * @create: 2019-1-13 08:58
 * @version: 1.0
 */
public class BdpayConfig implements AccountConfig {
    /**
     * 商户appid
     */
    private String mchId;
    /**
     * 商户密钥.
     */
    private String mchKey;

    /**
     * 请求网关地址
     */
    private String gatewayUrl = "https://www.baifubao.com/api";

    /**
     * 订单有效期 单位毫秒
     */
    private int timeout = Constants.ORDER_ACTIVE_TIME;

    /**
     * 签名方式 MD5,SHA-1
     */
    private String signType = BdpaySignType.MD5.value();

    /**
     * 编码GBK
     */
    private String charset = "1";

    /**
     * 响应数据格式 XML
     */
    private String format = "1";

    /**
     * 版本
     */
    private String version = "2";

    /**
     * 是否重试
     */
    private boolean retry;

    /**
     * 初始化百度钱包配置
     *
     * @param configParam
     * @return
     */
    public static BdpayConfig build(String configParam) {
        BdpayConfig config = JSON.parseObject(configParam, BdpayConfig.class, jsonConfig);
        config.timeout *= (60 * 1000);
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

    public String getCharset() {
        return charset;
    }

    public String getVersion() {
        return version;
    }

    public String getFormat() {
        return format;
    }
}