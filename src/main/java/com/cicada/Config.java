package com.cicada;

import com.dotin.common.utils.PropertiesUtils;

/**
 * 系统配置信息
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public class Config {

    private final static String config = "setting";

    private final static String env = PropertiesUtils.getPropertiesAttribute(config, "env.name");
    private final static String flag = PropertiesUtils.getPropertiesAttribute(config, "env.flag");
    private final static String domain = PropertiesUtils.getPropertiesAttribute(config, "domain.addr");

    private final static com.dotin.lock.bean.Config lockConfig = new com.dotin.lock.bean.Config(30);

    /**
     * 当前环境名称
     *
     * @return
     */
    public static String getEnv() {
        return env;
    }

    /**
     * 当前环境标示
     *
     * @return
     */
    public static String getFlag() {
        return flag;
    }

    public static String getDomain() {
        return domain;
    }

    /**
     * 请求支付渠道网络超时时间
     *
     * @return
     */
    public static int getReqTimeout() {
        return 30000;
    }

    /**
     * 同步锁重试次数
     *
     * @return
     */
    public static com.dotin.lock.bean.Config getLockConfig() {
        return lockConfig;
    }
}