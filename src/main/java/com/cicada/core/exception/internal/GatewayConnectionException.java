package com.cicada.core.exception.internal;

import com.cicada.core.exception.basic.ChannelException;

public class GatewayConnectionException extends ChannelException {

    public GatewayConnectionException() {
    }

    public GatewayConnectionException(String m) {
        super(m);
    }

    public GatewayConnectionException(String m, Throwable e) {
        super(m, e);
    }

    @Override
    public String getCode() {
        return "B.2." + "CONN_ERR";
    }
}
