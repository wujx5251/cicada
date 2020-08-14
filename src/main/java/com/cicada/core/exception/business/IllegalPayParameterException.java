package com.cicada.core.exception.business;

import com.cicada.core.exception.basic.BusinessException;

public class IllegalPayParameterException extends BusinessException {

    public IllegalPayParameterException(String m) {
        super(m);
    }

    public IllegalPayParameterException(String m, Exception e) {
        super(m, e);
    }

    @Override
    public String getCode() {
        return code + "." + "INVALID_PARAM";
    }
}
