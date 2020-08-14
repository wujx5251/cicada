package com.cicada.component.wxpay.enums;

import com.cicada.core.enums.Valuable;

/**
 * 微信支付接口
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public enum WxpayAction implements Valuable {
    CREATE_ORDER("/pay/unifiedorder", "统一下单"),
    MICRO_PAY("/pay/micropay", "付款码支付"),
    QUERY("/pay/orderquery", "支付查询"),
    CLOSE("/pay/closeorder", "支付关闭"),
    REFUND("/secapi/pay/refund", "退款"),
    CANCEL("/secapi/pay/reverse", "支付撤销"),
    REFUND_QUERY("/pay/refundquery", "退款结果查询"),
    BILL("/pay/downloadbill", "对账单下载"),
    SANDBOX("/pay/getsignkey", "沙箱key获取");

    private String value;
    private String desc;

    WxpayAction(String value, String desc) {
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