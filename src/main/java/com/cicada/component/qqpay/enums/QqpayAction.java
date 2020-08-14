package com.cicada.component.qqpay.enums;

import com.cicada.core.enums.Valuable;

/**
 * qq支付接口
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public enum QqpayAction implements Valuable {
    CREATE_ORDER("/cgi-bin/pay/qpay_unified_order.cgi", "统一下单"),
    MICRO_PAY("/cgi-bin/pay/qpay_micro_pay.cgi", "付款码支付"),
    QUERY("/cgi-bin/pay/qpay_order_query.cgi", "支付查询"),
    CLOSE("/cgi-bin/pay/qpay_close_order.cgi", "支付关闭"),
    REFUND("/cgi-bin/pay/qpay_refund.cgi", "退款"),
    CANCEL("/cgi-bin/pay/qpay_reverse.cgi", "支付撤销"),
    REFUND_QUERY("/cgi-bin/pay/qpay_refund_query.cgi", "退款结果查询"),
    BILL("/cgi-bin/sp_download/qpay_mch_statement_down.cgi", "对账单下载");

    private String value;
    private String desc;

    QqpayAction(String value, String desc) {
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