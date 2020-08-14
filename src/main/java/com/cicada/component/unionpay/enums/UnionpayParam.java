package com.cicada.component.unionpay.enums;

import com.cicada.core.enums.Valuable;

/**
 * 银联请求参数
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public enum UnionpayParam implements Valuable {

    VERSION("version", "版本号"),
    CHARSET("encoding", "编码方式"),
    CERT_ID("certId", "证书 ID"),
    CHANNEL_TYPE("channelType", "渠道类型"),
    ACCESS_TYPE("accessType", "接入类型"),
    MER_ID("merId", "商户代码"),
    NOTIFY_URL("backUrl", "后台通知地址"),
    RETURN_URL("frontUrl", "前台通知地址"),
    QUIT_URL("frontFailUrl", "失败交易前台跳转地址"),
    SIGN_TYPE("signMethod", "签名方法"),

    SIGN("signature", "签名"),
    TXN_TYPE("txnType", "交易类型"),
    TXN_SUB_TYPE("txnSubType", "交易子类"),
    BIZ_TYPE("bizType", "产品类型"),

    ENCRYPT_CERT_ID("encryptCertId", "敏感信息证书id"),
    CUSTOMER_INFO("customerInfo", "银行卡验证信息及身份信息"),
    CARD_NO("accNo", "卡号"),
    TOKEN_PAY_DATA("tokenPayData", "标记化支付信息域"),

    TRADE_NO("orderId", "商户订单号"),
    TIMESTAMP("txnTime", "订单发送时间"),
    AMOUNT("txnAmt", "交易金额"),
    CURRENCY("currencyCode", "交易币种"),
    TIMEOUT("orderTimeout", "订单接收超时时间（防钓鱼使用）"),
    PAY_TIMEOUT("payTimeout", "订单支付超时时间"),
    SUBJECT("orderDesc", "订单描述"),
    INS_CODE("issInsCode", "发卡机构代码"),

    CERTIF_TP("certifTp", "证件类型"),
    CERTIF_ID("certifId", "持卡人证件号码"),
    HOLDER_NAME("customerNm", "持卡人姓名"),
    SMS_CODE("smsCode", "短信验证码"),
    PHONE_NO("phoneNo", "手机号码"),
    CVV2("cvn2", "卡背面的cvn2三位数字"),
    EXPRIRED("expired", "有效期"),
    ENCRYPT_INFO("encryptedInfo", "加密信息域"),
    ACC_NO("accNo", "卡号"),
    ACC_TYPE("accType", "账号类型(卡介质)"),
    CARD_TYPE("payCardType", "支付卡类型"),

    TOKEN("token", "支付标记"),
    TRID("trId", "标记请求者ID"),
    TOKEN_TYPE("tokenType", "标记类型"),

    QR_CODE("qrNo", "C2B 码"),


    ORIG_QRY_ID("origQryId", "原交易查询流水号"),
    BILL_DATE("settleDate", "对账日期"),
    BILL_FILE_TYPE("fileType", "对账文件类型"),;

    private String value;
    private String desc;

    UnionpayParam(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String desc() {
        return desc;
    }

}