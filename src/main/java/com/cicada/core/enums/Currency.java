package com.cicada.core.enums;

import com.cicada.core.exception.business.CurrencyNotSupportException;
import com.dotin.common.utils.StringUtils;

/**
 * 交易货币类型
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum Currency implements Valuable {

    CNY("CNY", "人民币");

    private String value;
    private String desc;

    Currency(String value, String desc) {
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

    public static Currency from(String name, Currency def) {
        if (StringUtils.isEmpty(name)) {
            return def;
        }

        for (Currency currency : Currency.values()) {
            if (StringUtils.equals(name, currency.value())) {
                return currency;
            }
        }
        throw new CurrencyNotSupportException(name);
    }

    @Override
    public String toString() {
        return value;
    }
}
