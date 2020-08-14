package com.cicada.component.jdpay.emuns;

import com.cicada.core.enums.Valuable;

/**
 * 京东支付交易类型
 *
 * @author: WUJXIAO
 * @create: 2019-01-26 19:58
 * @version: 1.0
 **/
public enum JdpayTradeType implements Valuable {

    GEN("GEN", "普通支付"),
    QR("QR", "扫一扫支付"),
    CONSUME("0", "消费"),
    REFUND("1", "退款");


    private String value;
    private String desc;

    JdpayTradeType(String value, String desc) {
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