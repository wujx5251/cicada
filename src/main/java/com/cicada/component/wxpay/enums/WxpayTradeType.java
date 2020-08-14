package com.cicada.component.wxpay.enums;

import com.cicada.core.enums.Valuable;

/**
 * 微信支付类型
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public enum WxpayTradeType implements Valuable {
    APP("APP", "app支付"),
    JSAPI("JSAPI", "js支付"),
    MWEB("MWEB", "h5支付"),
    NATIVE("NATIVE", "二维码支付"),
    MICROPAY("MICROPAY", "付款码支付");

    private String value;
    private String desc;

    WxpayTradeType(String value, String desc) {
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