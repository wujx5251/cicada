package com.cicada.core.exception.business;

import com.cicada.core.exception.basic.BusinessException;

public class IllegalPaySignatureException extends BusinessException {

    public IllegalPaySignatureException() {
        super("Illegal pay signature.");
    }

    @Override
    public String getCode() {
        return code + "." + "INVALID_SIGN";
    }
}
