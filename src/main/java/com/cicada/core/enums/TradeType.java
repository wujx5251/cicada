package com.cicada.core.enums;

import com.dotin.common.utils.NumberUtils;

/**
 * 交易类型
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum TradeType implements Valuable {

    FETCH(1, "收款"),
    PAY(2, "付款"),
    REFUND(3, "退款"),
    CANCEL(4, "撤销");

    private int value;
    private String desc;

    TradeType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer value() {
        return value;
    }

    @Override
    public String desc() {
        return desc;
    }


    public static TradeType from(Integer value) {
        if (null == value) {
            return null;
        }
        for (TradeType em : TradeType.values()) {
            if (NumberUtils.isEquals(em.value, value)) {
                return em;
            }
        }
        return null;
    }
}
