package com.cicada.core.exception.business;

import com.cicada.core.exception.basic.PayException;

public class IllegalCardException extends PayException {

    public IllegalCardException(String value) {
        super("Illegal card " + value);
    }

    @Override
    public String getCode() {
        return code + "." + "INVALID_CARD";
    }
}
