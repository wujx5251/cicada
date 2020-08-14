package com.cicada.component.alipay.enums;

import com.cicada.core.enums.Valuable;

/**
 * 支付宝请求参数
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public enum AlipayParam implements Valuable {
    APP_ID("app_id", "开发者的应用ID"),
    METHOD("method", "接口名称"),
    FORMAT("format", "返回数据格式"),
    CHARSET("charset", "数据编码"),
    VERSION("version", "接口版本号"),
    TIMESTAMP("timestamp", "时间戳"),
    SIGN_TYPE("sign_type", "签名类型"),
    CONTENT("biz_content", "业务参数"),
    NOTIFY_URL("notify_url", "异步通知地址"),
    RETURN_URL("return_url", "同步通知地址"),
    SIGN("sign", "签名串"),

    QUIT_URL("quit_url", "h5支付页面返回地址"),
    TRADE_NO("out_trade_no", "交易流水号"),
    REFUND_NO("out_request_no", "退款交易流水号"),
    AMOUNT("total_amount", "交易金额"),
    CURRENCY("trans_currency", "标价币种"),

    REFUND_AMOUNT("refund_amount", "退款金额"),
    REFUND_CURRENCY("refund_currency", "订单退款币种信息"),
    REFUND_DESC("refund_reason", "退款的原因说明"),

    SUBJECT("subject", "交易标题"),
    BODY("body", "交易详情"),
    TIMEOUT("timeout_express", "最晚付款时间"),
    PRODUCT("product_code", "支付宝签约的产品码"),
    QR_TIMEOUT("qr_code_timeout_express", "二维码有效时间"),

    SCENE("scene", "支付场景"),
    AUTH_CODE("auth_code", "支付授权码"),
    SETTLE_CURRENCY("settle_currency", "商户指定的结算币种"),


    BILL_DATE("bill_date", "对账单日期"),
    BILL_TYPE("bill_type", "账单类型"),

    RESPONSE("_response", "同步响应参数"),;


    private String value;
    private String desc;

    AlipayParam(String value, String desc) {
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