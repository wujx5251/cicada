package com.cicada.core.exception.business;

import com.cicada.core.exception.basic.BusinessException;

public class NotSupportException extends BusinessException {

    public NotSupportException(String value) {
        super(value);
    }

    @Override
    public String getCode() {
        return code + "." + "NOT_SUPPORT";
    }
}
