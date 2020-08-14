package com.cicada.core.exception.business;

import com.cicada.core.exception.basic.BusinessException;

public class IllegalChanelSignatureException extends BusinessException {

    public IllegalChanelSignatureException() {
        super("Illegal channel signature.");
    }

    @Override
    public String getCode() {
        return code + "." + "INVALID_CHN_SIGN";
    }
}
