package com.cicada.core.exception.basic;

/**
 * 渠道异常
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public abstract class ChannelException extends PayException {

    public ChannelException() {}

    public ChannelException(String m) {
        super(m);
    }

    public ChannelException(String m, Throwable e) {
        super(m, e);
    }
}
