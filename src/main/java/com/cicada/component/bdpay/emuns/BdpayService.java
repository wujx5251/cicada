package com.cicada.component.bdpay.emuns;

import com.cicada.core.enums.Valuable;

/**
 * 百度钱包服务编号
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public enum BdpayService implements Valuable {
    PAY("1", "支付"),
    QUERY("11", "付款查询"),
    REFUND_QUERY("12", "退款查询"),;

    private String value;
    private String desc;

    BdpayService(String value, String desc) {
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