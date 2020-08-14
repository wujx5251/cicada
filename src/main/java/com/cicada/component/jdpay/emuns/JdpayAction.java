package com.cicada.component.jdpay.emuns;

import com.cicada.core.enums.Valuable;

/**
 * 京东支付请求地址
 *
 * @author: WUJXIAO
 * @create: 2019-01-26 19:58
 * @version: 1.0
 **/
public enum JdpayAction implements Valuable {

    CREATE_ORDER("/service/uniorder", "统一下单"),
    MICRO_PAY("/service/fkmPay", "付款码支付"),
    QUERY("/service/query", "支付查询"),
    REFUND("/service/refund", "退款"),
    CANCEL("/service/revoke", "支付撤销"),;


    private String value;
    private String desc;

    JdpayAction(String value, String desc) {
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