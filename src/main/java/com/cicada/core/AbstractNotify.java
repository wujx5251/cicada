package com.cicada.core;

import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 支付抽象接口
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public abstract class AbstractNotify implements Pay {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractNotify.class);

    /**
     * 返回结果渲染
     *
     * @param context
     * @param params
     * @return
     */
    abstract protected TradingResult render(TradingContext context, Object params);

}