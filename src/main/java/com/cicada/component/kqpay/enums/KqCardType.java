package com.cicada.component.kqpay.enums;

import com.cicada.core.enums.CardType;
import com.cicada.core.enums.Valuable;
import com.cicada.core.exception.business.CardTypeNotSupportException;
import com.dotin.common.utils.StringUtils;

/**
 * 卡类型枚举
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum KqCardType implements Valuable {

    DEBIT("0001", CardType.DEBIT),
    CREDIT("0002", CardType.CREDIT);

    private String value;
    private CardType desc;

    KqCardType(String value, CardType desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String desc() {
        return desc.desc();
    }

    public static KqCardType from(CardType value) {
        if (null == value)
            return null;
        for (KqCardType type : KqCardType.values()) {
            if (type.desc.equals(value)) {
                return type;
            }
        }

        throw new CardTypeNotSupportException(value.value());
    }

    public static CardType from(String value) {
        if (StringUtils.isEmpty(value))
            return null;
        for (KqCardType type : KqCardType.values()) {
            if (type.value.equals(value)) {
                return type.desc;
            }
        }

        throw new CardTypeNotSupportException(value);
    }
}
