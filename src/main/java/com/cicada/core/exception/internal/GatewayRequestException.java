package com.cicada.core.exception.internal;

import com.cicada.core.exception.basic.ChannelException;

public class GatewayRequestException extends ChannelException {

    public GatewayRequestException() {
    }

    public GatewayRequestException(String m) {
        super(m);
    }

    public GatewayRequestException(String m, Throwable e) {
        super(m, e);
    }

    @Override
    public String getCode() {
        return "B.2." + "REQ_ERR";
    }
}
