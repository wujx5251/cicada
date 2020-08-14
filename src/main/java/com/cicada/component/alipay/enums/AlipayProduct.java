package com.cicada.component.alipay.enums;

import com.cicada.core.enums.Valuable;

/**
 * 支付宝产品码
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public enum AlipayProduct implements Valuable {
    APP("QUICK_MSECURITY_PAY", "app支付产品码"),
    WEB("FAST_INSTANT_TRADE_PAY", "web支付产品码"),
    WAP("QUICK_WAP_PAY", "wap支付产品码"),
    F2F("FACE_TO_FACE_PAYMENT", "面对面支付");


    private String value;
    private String desc;

    AlipayProduct(String value, String desc) {
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