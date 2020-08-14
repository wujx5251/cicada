package com.cicada.core.exception.business;

import com.cicada.core.exception.basic.BusinessException;


public class RecordNotFoundException extends BusinessException {

    public RecordNotFoundException(String no) {

        super("could not found trading record info by record id '" + no + "'");
    }

    @Override
    public String getCode() {
        return code + "." + "NOT_RECORD";
    }
}
