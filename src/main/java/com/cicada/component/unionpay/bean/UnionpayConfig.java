package com.cicada.component.unionpay.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.cicada.Constants;
import com.cicada.component.unionpay.enums.UnionpaySignType;
import com.cicada.core.bean.AccountConfig;
import com.cicada.core.serializer.CertSerializer;
import com.dotin.common.utils.crypto.Cert;

import java.util.Date;


/**
 * 银联配置参数
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public class UnionpayConfig implements AccountConfig {
    /**
     * 商户appid
     */
    private String mchId;

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
     * 银联公钥证书
     */
    @JSONField(deserializeUsing = CertSerializer.class, serializeUsing = CertSerializer.class)
    private Cert publicCert;

    /**
     * 银联根证书 5.1.0
     */
    @JSONField(deserializeUsing = CertSerializer.class, serializeUsing = CertSerializer.class)
    private Cert rootCert;

    /**
     * 银联中间公钥证书，做证书校验使用5.1.0
     */
    @JSONField(deserializeUsing = CertSerializer.class, serializeUsing = CertSerializer.class)
    private Cert middleCert;

    /**
     * 敏感加密证书
     */
    @JSONField(deserializeUsing = CertSerializer.class, serializeUsing = CertSerializer.class)
    private Cert secureCert;

    /**
     * 请求者编号,无跳转产品使用
     */
    private String trId;

    /**
     * accNo是否加密
     */
    private boolean accEnc = true;

    // 请求网关地址
    private String gatewayUrl = "https://gateway.95516.com/gateway/api";
    //private String gatewayUrl = "https://gateway.test.95516.com/gateway/api";

    /**
     * 订单有效期 单位分钟
     */
    private int timeout = Constants.ORDER_ACTIVE_TIME;

    /**
     * 安全密钥 RSA2,SM3使用
     */
    private String secureKey;
    /**
     * 签名方式,
     * 01：（表示采用 RSA 签名）HASH 表示散列算法
     * 11：支持散列方式验证 SHA-256
     * 12：支持散列方式验证 SM3
     */
    public String signType = UnionpaySignType.RSA.value();

    /**
     * 是否重试
     */
    private boolean retry;

    /**
     * 编码
     */
    private String charset = "UTF-8";
    /**
     * 版本号
     */
    private String version = "5.1.0";

    /**
     * 通道类型05：语音 07：互联网 , 08：移动
     */
    private String channelType = "07";

    /**
     * 接入类型 0：普通商户直连接入 2：平台类商户接入
     */
    private String accessType = "0";


    /**
     * 初始化银联配置
     *
     * @param configParam
     * @return
     */
    public static UnionpayConfig build(String configParam) {
        UnionpayConfig config = JSON.parseObject(configParam, UnionpayConfig.class, jsonConfig);
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

    public Cert getMchCert() {
        return mchCert;
    }

    public String getMchCertPwd() {
        return mchCertPwd;
    }

    public Cert getPublicCert() {
        return publicCert;
    }

    public Cert getRootCert() {
        return rootCert;
    }

    public Cert getMiddleCert() {
        return middleCert;
    }

    public Cert getSecureCert() {
        return secureCert;
    }

    public String getTrId() {
        return trId;
    }

    public boolean isAccEnc() {
        return accEnc;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getSecureKey() {
        return secureKey;
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
        return new Date(System.currentTimeMillis() + timeout * 60 * 1000);
    }

    public String getCharset() {
        return charset;
    }

    public String getVersion() {
        return version;
    }

    public String getChannelType() {
        return channelType;
    }

    public String getAccessType() {
        return accessType;
    }
}