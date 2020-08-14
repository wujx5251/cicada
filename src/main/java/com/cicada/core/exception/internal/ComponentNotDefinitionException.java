package com.cicada.core.exception.internal;


import com.cicada.core.exception.basic.InternalException;

/**
 * 组件未定义异常
 *
 * @author: WUJXIAO
 * @create: 2018-12-21 15:58
 * @version: 1.0
 */
public class ComponentNotDefinitionException extends InternalException {

    public ComponentNotDefinitionException(String channelName, String method) {
        super("component for channel: '" + channelName + "', method '" + method + "' not found!");
    }

    @Override
    public String getCode() {
        return "B.2." + "NOT_COMPONET";
    }
}
