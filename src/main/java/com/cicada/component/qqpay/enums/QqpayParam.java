package com.cicada.component.qqpay.enums;

import com.cicada.core.enums.Valuable;

/**
 * 微信请求参数
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public enum QqpayParam implements Valuable {

    APP_ID("appid", "开发者的应用ID"),
    MCH_ID("mch_id", "商户号"),
    TRADE_TYPE("trade_type", "交易类型"),
    NONCE_STR("nonce_str", "随机字符串"),
    CLIENT_IP("spbill_create_ip", "终端IP"),
    SIGN_TYPE("sign_type", "签名类型"),
    NOTIFY_URL("notify_url", "异步通知地址"),
    RETURN_URL("redirect_url", "同步通知地址"),
    SIGN("sign", "签名串"),

    TRADE_NO("out_trade_no", "交易流水号"),
    REFUND_NO("out_refund_no", "商户退款单号"),

    CURRENCY("fee_type", "货币类型"),
    AMOUNT("total_fee", "总金额"),
    REFUND_AMOUNT("refund_fee", "退款金额"),
    REFUND_CURRENCY("refund_fee_type", "退款货币种类"),
    SUBJECT("body", "商品描述"),
    STRAT_TIME("time_start", "交易起始时间"),
    TIMEOUT("time_expire", "订单失效时间"),
    REFUND_DESC("refund_desc", "退款原因"),


    OPEN_ID("openid", "用户标识"),
    SCENE_INFO("scene_info", "场景信息"),
    AUTH_CODE("auth_code", "场景信息"),
    PRODUCT_ID("product_id", "商品ID"),

    BILL_DATE("bill_date", "对账单日期"),
    BILL_TYPE("bill_type", "账单类型"),
    BILL_RESULT_TYPE("tar_type", "压缩账单"),


    /*---------------------APP支付字符串-----------------*/
    PARTNERID("partnerid", "商户号"),
    PREPAYID("prepayid", "预支付交易会话ID"),
    PACKAGE("package", "扩展字段"),
    NONCESTR("noncestr", "随机字符串"),
    TIMESTAMP("timestamp", "时间戳"),

    /*---------------------JSAPI支付字符串-----------------*/
    APPID("appId", "开发者的应用ID"),
    TIMESTAMP_JS("timeStamp", "时间戳"),
    NONCESTR_JS("nonceStr", "随机字符串"),
    SIGNTYPE("signType", "签名方式"),
    PAYSIGN("paySign", "签名"),
    XML_ROOT("xml", "xml根"),
    ;


    private String value;
    private String desc;

    QqpayParam(String value, String desc) {
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