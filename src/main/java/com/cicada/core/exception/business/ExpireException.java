package com.cicada.core.exception.business;

import com.cicada.core.exception.basic.PayException;

public class ExpireException extends PayException {

    public ExpireException() {
        super("trade record expired！");
    }

    public ExpireException(String no) {
        super("the trade record \"" + no + "\" has expired!");
    }

    @Override
    public String getCode() {
        return code + "." + "EXPIRED";
    }
}
