package com.cicada.core.exception.business;

import com.cicada.core.exception.basic.PayException;

public class IllegalTradeAmountException extends PayException {

    public IllegalTradeAmountException() {
        super("Illegal trade amount！");
    }

    public IllegalTradeAmountException(String msg) {
        super(msg);
    }

    @Override
    public String getCode() {
        return code + "." + "INVALID_AMOUNT";
    }
}
