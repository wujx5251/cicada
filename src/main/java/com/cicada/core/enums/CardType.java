package com.cicada.core.enums;

import com.cicada.core.exception.business.CardTypeNotSupportException;
import com.dotin.common.utils.StringUtils;

/**
 * 卡类型枚举,银联为蓝本
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum CardType implements Valuable {

    CREDIT("01", "借记卡"),
    DEBIT("02", "贷记卡");

    private String value;
    private String desc;

    CardType(String value, String desc) {
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

    public static CardType from(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        for (CardType type : CardType.values()) {
            if (type.value().equals(value)) {
                return type;
            }
        }

        throw new CardTypeNotSupportException(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
