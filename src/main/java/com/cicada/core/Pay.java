package com.cicada.core;

import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;

/**
 * 统一支付接口
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public interface Pay {


    TradingResult handleNotify(TradingContext context);

    /**
     * 执行支付操作
     *
     * @param context
     * @return
     */
    TradingResult doPay(TradingContext context);
}
