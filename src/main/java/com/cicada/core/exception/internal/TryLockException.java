package com.cicada.core.exception.internal;


import com.cicada.core.exception.basic.InternalException;

/**
 * 加锁异常
 *
 * @author: WUJXIAO
 * @create: 2018-12-21 15:58
 * @version: 1.0
 */
public class TryLockException extends InternalException {


    public TryLockException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "B.2." + "BUSY";
    }
}
