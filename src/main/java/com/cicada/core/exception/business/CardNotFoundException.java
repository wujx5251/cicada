package com.cicada.core.exception.business;


import com.cicada.core.exception.basic.BusinessException;

/**
 *
 */
public class CardNotFoundException extends BusinessException {

    public CardNotFoundException(String serialNumber) {
        super("not found bind card by：'" + serialNumber + "'");
    }

    @Override
    public String getCode() {
        return code + "." + "NOT_FOUND_CARD";
    }
}
