package com.cicada.core.exception.business;

import com.cicada.core.exception.basic.BusinessException;


public class ApplicationNotFoundException extends BusinessException {

    private static final long serialVersionUID = -4552454224233925078L;

    public ApplicationNotFoundException(String no) {

        super("Could not found application info by app id '" + no + "'");
    }

    @Override
    public String getCode() {
        return code + "." + "INVALID_APP";
    }
}
