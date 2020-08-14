package com.cicada.component.kqpay.enums;

import com.cicada.core.enums.Valuable;

/**
 * 快钱请求参数
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public enum KqpayParam implements Valuable {
    MERC_ID("merchantId", "商户号"),
    TERM_ID("terminalId", "终端号"),
    TXN_TYPE("txnType", "交易类型"),
    INTER_STATUS("interactiveStatus", "消息状态"),
    AMOUNT("amount", "交易金额"),
    TRADE_NO("externalRefNumber", "客户订单号"),
    CUSTOMER_ID("customerId", "客户订单号"),
    PAY_TOKEN("payToken", "支付标记"),
    ORIG_TXN_TYPE("orignalTxnType", "原始交易类型 撤销需要"),
    REF_NUMBER("refNumber", "原始交易渠道流水号 撤销需要"),
    ORIG_REF_NUMBER("origRefNumber", "原始交易渠道流水号 退回需要"),
    AUTH_CODE("authorizationCode", "交易授权码"),

    BANK_ID("bankId", "发卡行编号"),
    CARD_TYPE("cardType", "卡类型"),
    SHORT_ACC_NO("storablePan", "短卡号"),//绑卡短信、确认、消费短信使用
    SHORT_CARD_NO("storableCardNo", "短卡号"),//交易使用，消费、预授权使用
    ACC_NO("pan", "卡号"),
    CARD_NO("cardNo", "卡号"),
    CERTIF_TP("idType", "证件类型"),
    CERTIF_ID("cardHolderId", "持卡人证件号码"),
    HOLDER_NAME("cardHolderName", "持卡人姓名"),
    SMS_CODE("smsCode", "短信验证码"),
    PHONE_NO("phoneNO", "手机号码"),
    CVV2("cvv2", "卡背面的cvn2三位数字"),
    EXPRIRED("expiredDate", "有效期"),

    BIND_TYPE("bindType", "客户订单号"),
    VALID_CODE("validCode", "短信验证码"),
    TOKEN("token", "授权码"),
    SP_FLAG("spFlag", "特殊交易标志"),

    TRADE_TIME("entryTime", "交易时间"),
    EXT_MAP("extMap", "扩展字段map节点"),
    EXT_DATA("extDate", "扩展数据"),
    EXT_DATA_KEY("key", "数据键"),
    EXT_DATA_VALUE("value", "数据值"),

    NOTIFY_URL("tr3Url", "异步通知地址"),
    WEB_NOTIFY_URL("bgUrl", "web异步通知地址"),
    WEB_RETURN_URL("pageUrl", "web同步通知地址"),

    PAY_TYPE("payType", "支付类型"),
    MOBILE_TYPE("mobileGateway", "移动网关类型"),


    MER_ACC_ID("merchantAcctId", "WEB支付商编"),
    ORDER_ID("orderId", "WEB支付订单号"),
    ORDER_AMOUNT("orderAmount", "WEB支付金额"),
    ORDER_TIME("orderTime", "WEB支付请求时间"),
    PRODUCT_NAME("productName", "WEB支付商品名称"),
    PRODUCT_DESC("productDesc", "WEB支付商品描述"),
    REDO_FLAG("redoFlag", "是否允许重复提交"),
    SUBMIT_TYPE("submitType", "提交方式"),
    TIME_OUT("orderTimeOut", "超时时间"),

    LANGUAGE("language", "语言"),
    CHARSET("inputCharset", "数据编码方式"),
    SIGN_TYPE("signType", "签名方式"),
    VERSION("version", "版本号"),
    SIGN("signMsg", "签名串"),
    XML_ROOT("MasMessage", "xml根"),
    ;


    private String value;
    private String desc;

    KqpayParam(String value, String desc) {
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