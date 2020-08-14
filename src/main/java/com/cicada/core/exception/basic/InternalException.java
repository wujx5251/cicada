package com.cicada.core.exception.basic;


/**
 * 支付内部异常
 *
 * @author: WUJXIAO
 * @create: 2018-12-18 15:58
 * @version: 1.0
 */
public abstract class InternalException extends PayException {

    private static final long serialVersionUID = 603411356798443242L;

    public InternalException() {
        this("Internal exception in CICADA！");
    }

    public InternalException(String m) {
        super(m);
    }

    public InternalException(String m, Throwable e) {
        super(m, e);
    }
}
