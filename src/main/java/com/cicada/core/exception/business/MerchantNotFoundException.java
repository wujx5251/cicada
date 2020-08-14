package com.cicada.core.exception.business;

import com.cicada.core.exception.basic.BusinessException;


public class MerchantNotFoundException extends BusinessException {


    public MerchantNotFoundException(String no) {

        super("Could not found merchant account by mch id '" + no + "'");
    }

    @Override
    public String getCode() {
        return code + "." + "INVALID_MCH";
    }
}
