package com.cicada.core.exception.internal;

import com.cicada.core.exception.basic.ChannelException;

public class GatewayTimeOutException extends ChannelException {

    public GatewayTimeOutException() {
    }

    public GatewayTimeOutException(String m) {
        super(m);
    }

    public GatewayTimeOutException(String m, Throwable e) {
        super(m, e);
    }

    @Override
    public String getCode() {
        return "B.2." + "READ_ERR";
    }
}
