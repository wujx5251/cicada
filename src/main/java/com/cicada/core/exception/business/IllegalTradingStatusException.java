package com.cicada.core.exception.business;

import com.cicada.core.exception.basic.PayException;

public class IllegalTradingStatusException extends PayException {

    public IllegalTradingStatusException(String value) {
        super("illegal trading status, current status：'" + value + "'");
    }

    @Override
    public String getCode() {
        return code + "." + "INVALID_STATUS";
    }
}
