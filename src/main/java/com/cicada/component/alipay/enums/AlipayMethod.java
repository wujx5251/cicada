package com.cicada.component.alipay.enums;

import com.cicada.core.enums.Valuable;

/**
 * 支付宝接口
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public enum AlipayMethod implements Valuable {
    APP_PAY("alipay.trade.app.pay", "app支付"),
    MWEB_PAY("alipay.wap.create.direct.pay.by.user", "h5支付（mapi）"),
    WAP_PAY("alipay.trade.wap.pay", "h5支付"),
    WEB_PAY("alipay.trade.page.pay", "网页支付"),
    SCAN_PAY("alipay.trade.precreate", "扫描支付"),
    QR_PAY("alipay.trade.pay", "二维码支付"),
    QUERY("alipay.trade.query", "支付状态查询"),
    CANCEL("alipay.trade.cancel", "支付取消"),
    CLOSE("alipay.trade.close", "关闭支付"),
    REFUND("alipay.trade.refund", "退款"),
    REFUND_QUERY("alipay.trade.fastpay.refund.query", "退款查询"),
    BILL("alipay.data.dataservice.bill.downloadurl.query", "对账");

    private String value;
    private String desc;

    AlipayMethod(String value, String desc) {
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