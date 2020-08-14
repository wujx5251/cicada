package com.cicada.core.bean;

import com.alibaba.fastjson.parser.ParserConfig;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付账户配置参数
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public interface AccountConfig extends Serializable {

    ParserConfig jsonConfig = new ParserConfig(true);

    /**
     * 渠道商户编号
     *
     * @return
     */
    String getMchId();

    /**
     * 渠道应用id
     *
     * @return
     */
    String getAppId();

    /**
     * 是否进行网络重试
     *
     * @return
     */
    boolean isRetry();

    /**
     * 获取订单最后有效时间
     *
     * @return
     */
    Date getLastTime();

}