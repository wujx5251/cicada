package com.cicada.core;

import java.util.Map;

/**
 * 参数安全工具
 * Created by jinxiao.wu
 */
public interface Security<T> {

    /**
     * 参数签名
     *
     * @param config
     * @param params
     * @return
     */
    <V> String sign(T config, Map<String, V> params);

    /**
     * 参数验签
     *
     * @param config
     * @param params
     * @return
     */
    <V> boolean verify(T config, Map<String, V> params);

}