package com.cicada.core.exception.basic;

/**
 * 业务异常
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public abstract class BusinessException extends PayException {

    public BusinessException() { super("业务异常！"); }

    public BusinessException(String m) { super(m); }

    public BusinessException(String m, Throwable e) { super(m, e); }

}
