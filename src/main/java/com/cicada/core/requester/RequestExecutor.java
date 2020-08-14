package com.cicada.core.requester;


import com.cicada.core.bean.TradingContext;
import com.dotin.common.net.http.Response;

import java.util.Map;

/**
 * 请求处理器
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public interface RequestExecutor<T> {

    /**
     * 请求渠道交易
     *
     * @param context 交易上下文
     * @param params  功能参数
     * @return 渠道同步交易响应的数据
     */
    Response execute(TradingContext context, Map<String, Object> params);

    /**
     * 数据验签
     *
     * @param context
     * @param rsp
     * @return
     */
    boolean verify(TradingContext context, T rsp);

    /**
     * 渠道返回数据解析处理
     *
     * @param context 交易上下文
     * @param rsp     返回数据
     * @return
     */
    T parse(TradingContext context, Response rsp);
}
