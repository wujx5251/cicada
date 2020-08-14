package com.cicada.core.bean;

/**
 * 渠道返回结果
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public interface Response {

    /**
     * 获取渠道流水号
     *
     * @return
     */
    String getReplyId();

    /**
     * 获取扩展数据
     *
     * @return
     */
    String getExtra();
}