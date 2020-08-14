package com.cicada.component.unionpay.enums;

import com.cicada.core.enums.Valuable;

/**
 * 交易类型枚举
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum UnionpayTxnType implements Valuable {

    QUERY_STATUS("00", "查询交易"),
    CONSUME("01", "消费"),
    AUTH_FREEZE("02", "预授权"),
    AUTH_COMPLETE("03", "预授权完成"),
    REFUND("04", "退货"),
    FETCH("11", "代收"),
    PAYMENT("12", "代付"),
    CANCEL("31", "消费撤销"),
    AUTH_CANCEL("32", "预授权撤销"),
    AUTH_COMPLETE_CANCEL("33", "预授权完成撤销"),
    REAL_AUTH("72", "实名认证-建立绑定关系"),
    REMOVE_CARD("74", "开通解除"),
    BILL("76", "对账"),
    SEND_SMS("77", "发送短信验证码交易"),
    OPEN_CARD_STATUS("78", "开通查询交易"),
    OPEN_CARD("79", "开通交易");


    private String value;
    private String desc;

    UnionpayTxnType(String value, String desc) {
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
