package com.cicada.component.unionpay.enums;

import com.cicada.core.enums.Valuable;

/**
 * 银联产品类型枚举
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum UnionpayBizType implements Valuable {

    QUERY_STATUS("000000", "交易状态查询"),
    B2C_GATEWAY_PAY("000201", "B2C网关支付"),
    TOKEN_PAY("000902", "Token 支付"),
    NOT_TAKEN_PAY("000301", "认证支付 2.0"),
    PAYMENT("000401", "代付"),
    FETCH("000501", "代收");

    private String value;
    private String desc;

    UnionpayBizType(String value, String desc) {
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
