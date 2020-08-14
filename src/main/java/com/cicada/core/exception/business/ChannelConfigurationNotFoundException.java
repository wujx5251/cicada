package com.cicada.core.exception.business;

import com.cicada.core.exception.basic.InternalException;

public class ChannelConfigurationNotFoundException extends InternalException {

    public ChannelConfigurationNotFoundException(String appId, String channel, String product) {

        super("Could not found channel config by appId:'" + appId + "' channle:'" + channel + "' product:'" + product + "'");
    }

    public ChannelConfigurationNotFoundException(String channel) {

        super("Could not found channel config by channle:" + channel);
    }

    public ChannelConfigurationNotFoundException(String channel, Throwable ex) {

        super("Could not found channel config by channle:" + channel, ex);
    }

    @Override
    public String getCode() {
        return code + "." + "UNKNOW_CHANNEL_CONFIG";
    }
}
