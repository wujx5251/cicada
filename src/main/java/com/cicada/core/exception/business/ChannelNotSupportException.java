package com.cicada.core.exception.business;


import com.cicada.core.exception.basic.BusinessException;

public class ChannelNotSupportException extends BusinessException {

    public ChannelNotSupportException(String name) {
        super("not support channel for '" + name + "'！");
    }

    @Override
    public String getCode() {
        return code + "." + "INVALID_CHANNEL";
    }
}
