package com.cicada.component.bdpay.emuns;

import com.cicada.core.enums.Currency;
import com.cicada.core.enums.Valuable;
import com.cicada.core.exception.business.CurrencyNotSupportException;

/**
 * 百度钱包货币类型
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public enum BdpayCurrency implements Valuable {
    CNY("1", Currency.CNY);

    private String value;
    private Currency desc;

    BdpayCurrency(String value, Currency desc) {
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

    public static BdpayCurrency from(Currency name) {
        for (BdpayCurrency currency : BdpayCurrency.values()) {
            if (currency.desc.equals(name)) {
                return currency;
            }
        }
        throw new CurrencyNotSupportException(name.value());
    }

}