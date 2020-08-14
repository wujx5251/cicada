package com.cicada.core.enums;

/**
 * 支付組件类型
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum ComponetType implements Valuable {

    APP_PAY("trade.app.pay", "APP支付接口"),
    WAP_PAY("trade.wap.pay", "H5支付接口"),
    QR_PAY("trade.qr.pay", "二维码支付接口"),
    SCAN_PAY("trade.scan.pay", "扫描支付接口"),
    WEB_PAY("trade.web.pay", "PC支付接口"),
    JS_PAY("trade.js.pay", "JSAPI支付接口"),
    APPLET_PAY("trade.applet.pay", "小程序支付接口"),
    QUICK_PAY("trade.quick.pay", "无卡快捷支付接口"),
    AUTH_PAY("trade.auth.pay", "无卡预授权支付接口"),
    AUTH_CFM_PAY("trade.auth.cfm.pay", "无卡预授权完成支付接口"),
    QUERY("trade.query", "交易结果查询"),
    PAY_QUERY("trade.pay.query", "支付结果接口"),
    REFUND_QUERY("trade.refund.query", "退货结果查询接口"),
    CANCEL("trade.cancel", "交易取消接口"),
    CLOSE("trade.close", "交易关闭接口"),
    QUICK_CANCEL("trade.quick.cancel", "无卡支付取消接口"),
    AUTH_CANCEL("trade.auth.cancel", "无卡预授权取消接口"),
    AUTH_CFM_CANCEL("trade.auth.cfm.cancel", "无卡预授权完成取消接口"),
    REFUND("trade.refund", "支付退货接口"),
    SMS_SEND("trade.sms.send", "无卡短信发送接口"),
    BIND_SMS_SEND("trade.bind.sms.send", "无卡绑卡短信发送接口"),
    CARD_INFO("trade.card.info", "无卡银行卡信息查询接口"),
    CARD_BIND("trade.card.bind", "无卡银行卡绑定接口"),
    BIND_QUERY("trade.bind.query", "无卡银行卡绑定状态查询接口"),
    BIND_REMOVE("trade.card.unbind", "无卡银行卡绑定解除接口"),
    BILL_DOWN("trade.bill.down", "交易对账单下载接口"),;


    private String value;
    private String desc;

    ComponetType(String value, String desc) {
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
