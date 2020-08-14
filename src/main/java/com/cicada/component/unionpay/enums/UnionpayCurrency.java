package com.cicada.component.unionpay.enums;

import com.cicada.core.enums.Currency;
import com.cicada.core.enums.Valuable;
import com.cicada.core.exception.business.CurrencyNotSupportException;

/**
 * 银联支付货币类型
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public enum UnionpayCurrency implements Valuable {
    CNY("156", Currency.CNY);

    private String value;
    private Currency desc;

    UnionpayCurrency(String value, Currency desc) {
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

    public static UnionpayCurrency from(Currency name) {
        for (UnionpayCurrency currency : UnionpayCurrency.values()) {
            if (currency.desc.equals(name)) {
                return currency;
            }
        }
        throw new CurrencyNotSupportException(name.value());
    }

}