package com.cicada.core.exception.business;

import com.cicada.core.exception.basic.PayException;

public class CardTypeNotSupportException extends PayException {

    public CardTypeNotSupportException(String value) {
        super("Card type：'" + value + "' not supported.");
    }

    @Override
    public String getCode() {
        return code + "." + "INVALID_CARD_TYPE";
    }
}
