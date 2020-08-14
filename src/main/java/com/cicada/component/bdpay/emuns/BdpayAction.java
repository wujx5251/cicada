package com.cicada.component.bdpay.emuns;

import com.cicada.core.enums.Valuable;

/**
 * 百度钱包请求地址
 *
 * @author: WUJXIAO
 * @create: 2019-01-26 19:58
 * @version: 1.0
 **/
public enum BdpayAction implements Valuable {

    WEB_PAY("/0/pay/0/direct/0", "PC支付"),
    WAP_PAY("/0/pay/0/wapdirect/0", "H5支付"),
    QUERY("/0/query/0/pay_result_by_order_no", "支付查询"),
    REFUND("/0/refund", "退款"),
    REFUND_QUERY("/0/refund/0/query", "退款查询"),;


    private String value;
    private String desc;

    BdpayAction(String value, String desc) {
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