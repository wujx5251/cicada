package com.cicada.component.jdpay.emuns;

import com.cicada.core.enums.Valuable;

/**
 * 京东支付请求参数
 *
 * @author: WUJXIAO
 * @create: 2019-01-26 19:58
 * @version: 1.0
 **/
public enum JdpayParam implements Valuable {

    VERSION("version", "版本号"),
    MCH_ID("merchant", "商户号"),
    TRADE_NO("tradeNum", "交易流水号"),
    ORI_TRADE_NO("oTradeNum", "原交易流水号"),
    TRADE_TIME("tradeTime", "交易时间"),
    AMOUNT("amount", "交易金额"),
    CURRENCY("currency", "货币种类"),
    SUBJECT("tradeName", "交易名称"),
    BODY("tradeDesc", "交易描述"),
    TRADE_TYPE("tradeType", "交易类型"),
    EXPIRE("expireTime", "订单失效时长"),
    NOTIFY_URL("notifyUrl", "异步通知页面地址"),
    RETURN_URL("callbackUrl", "支付成功跳转路径"),
    ORDER_TYPE("orderType", "订单类型"),
    SIGN("sign", "交易信息签名"),
    AUTH_CODE("token", "付款码"),
    ENCRYPT("encrypt", "加密数据"),
    USER_ID("userId", "用户账号"),
    XML_ROOT("jdpay", "xml根"),
    RESPONSE("_RESPONSE", "京东返回数据"),;


    private String value;
    private String desc;

    JdpayParam(String value, String desc) {
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