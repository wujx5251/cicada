package com.cicada.component.unionpay.enums;

import com.cicada.core.enums.Valuable;

/**
 * 交易子类枚举
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum UnionpaySubTxnType implements Valuable {

    DEFAULT("00", "账号查询"),
    CONSUME("01", "消费"),
    QUERY("02", "订单号查询"),
    QR_CONSUME("06", "二维码消费"),
    QR_APPLY("07", "申请消费二维码");


    private String value;
    private String desc;

    UnionpaySubTxnType(String value, String desc) {
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
