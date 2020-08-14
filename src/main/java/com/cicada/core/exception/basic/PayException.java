package com.cicada.core.exception.basic;

/**
 * 支付异常
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public abstract class PayException extends com.cool.core.exception.BusinessException {

    public PayException() {}

    public PayException(String m) { super(m); }

    public PayException(String m, Throwable e) { super(m, e); }
}
