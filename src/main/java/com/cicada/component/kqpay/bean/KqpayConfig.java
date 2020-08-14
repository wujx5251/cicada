package com.cicada.component.kqpay.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.cicada.Constants;
import com.cicada.core.bean.AccountConfig;
import com.cicada.core.serializer.CertSerializer;
import com.dotin.common.net.http.Header;
import com.dotin.common.utils.Base64Utils;
import com.dotin.common.utils.crypto.Cert;

import java.util.Date;

/**
 * 快钱配置参数
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public class KqpayConfig implements AccountConfig {
    /**
     * 商户appid
     */
    private String mchId;
    /**
     * 商户密钥.
     */
    private String mchKey;

    /**
     * 商户证书base64
     */
    @JSONField(deserializeUsing = CertSerializer.class, serializeUsing = CertSerializer.class, ordinal = 99)
    private Cert mchCert;

    /**
     * 商户证书密码
     */
    private String mchCertPwd;

    /**
     * 快钱公钥证书
     */
    @JSONField(deserializeUsing = CertSerializer.class, serializeUsing = CertSerializer.class)
    private Cert publicCert;

    // 请求网关地址
    private String gatewayUrl = "https://mas.99bill.com/cnp/";
    //web https://www.99bill.com/gateway/recvMerchantInfoAction.htm
    //提现 https://www.99bill.com/webapp/services/BatchPayWS?wsdl;

    /**
     * http 请求base验证
     */
    private Header auth;

    /**
     * 终端号
     */
    private String terminalId;

    /**
     * 签名类型,该值为4，代表PKI加密方式
     */
    private String signType = "4";

    /**
     * 1代表中文显示，2代表英文显示。默认为1,该参数必填
     */
    private String language = "1";

    /**
     * 订单有效期 单位秒
     */
    private int timeout = Constants.ORDER_ACTIVE_TIME;

    /**
     * 订单有效期
     */
    private int lasttime = timeout;

    /**
     * 1代表UTF-8; 2代表GBK; 3代表GB2312
     */
    private String charset = "1";

    /**
     * 网关版本，固定值：1.0
     * web支付 v2.0
     * wap支付 mobile1.0
     */
    private String version = "1.0";

    /**
     * 是否重试
     */
    private boolean retry;

    /**
     * 初始化快钱配置
     *
     * @param configParam
     * @return
     */
    public static KqpayConfig build(String configParam) {
        KqpayConfig config = JSON.parseObject(configParam, KqpayConfig.class, jsonConfig);
        config.timeout *= 60;
        config.auth = new Header("Authorization", "Basic " + Base64Utils.encode(config.getMchId() + ":" + config.getMchKey()));
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

    public Cert getMchCert() {
        return mchCert;
    }

    public String getMchCertPwd() {
        return mchCertPwd;
    }

    public Cert getPublicCert() {
        return publicCert;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public Header getAuth() {
        return auth;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public String getSignType() {
        return signType;
    }

    public String getLanguage() {
        return language;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getCharset() {
        return charset;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public boolean isRetry() {
        return retry;
    }

    @Override
    public Date getLastTime() {
        return new Date(System.currentTimeMillis() + timeout * 1000);
    }
}