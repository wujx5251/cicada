package com.cicada.core.enums;

/**
 * 枚举接口
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public interface Valuable<K, V> {

    /**
     * 获取值
     */
    K value();

    /**
     * 获取描述
     */
    V desc();

}
