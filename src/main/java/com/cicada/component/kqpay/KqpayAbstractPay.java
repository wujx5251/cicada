package com.cicada.component.kqpay;

import com.cicada.core.bean.TradingContext;
import com.cicada.core.requester.RequestExecutor;

/**
 * 快钱
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public abstract class KqpayAbstractPay extends KqpayAbstractNotify {

    /**
     * 快钱接口，默认后台支付
     *
     * @return
     */
    abstract protected String getAction();

    @Override
    public RequestExecutor getRequestExecutor(TradingContext context) {
        return null;
    }

}