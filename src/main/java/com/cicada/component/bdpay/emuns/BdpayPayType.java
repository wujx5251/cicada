package com.cicada.component.bdpay.emuns;

import com.cicada.core.enums.Valuable;

/**
 * 百度钱包支付方式
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public enum BdpayPayType implements Valuable {
    BALANCE_PAY("1", "余额支付"),
    E_PAY("2", "网银支付"),
    BANK_PAY("3", "银行网关支付"),;

    private String value;
    private String desc;

    BdpayPayType(String value, String desc) {
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