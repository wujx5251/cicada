package com.cicada.core.exception.business;

import com.cicada.core.exception.basic.PayException;

public class CurrencyNotSupportException extends PayException {

    private static final long serialVersionUID = 6874593427951190551L;

    public CurrencyNotSupportException(String value) {
        super("Currency type：'" + value + "' not support！");
    }

    @Override
    public String getCode() {
        return code + "." + "INVALID_CURRENCY";
    }
}
