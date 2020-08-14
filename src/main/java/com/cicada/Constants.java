package com.cicada;

/**
 * 公共常量
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public interface Constants {

    /*-------------------redis缓存命名空间及redis集群组---------------------*/
    int NAMESPACE_DAO_MIN = 90050;
    int NAMESPACE_DAO_MAX = 90059;
    int LOCAL_CACHE_SIZE = 20000;
    String GROUP_NAME = "default";

    /*-------------------redis缓存过期时间单位---------------------*/
    long SECONDS = 60;
    long MINUTES = SECONDS * 1;
    long HOUR = MINUTES * 60;
    long DAY = HOUR * 24;
    long MONTH = DAY * 30;
    long YEAR = MONTH * 12;

    /*-------------------交易有效时间---------------------*/

    int ORDER_ACTIVE_TIME = 30;

    String SECURITY_KEY = "JzcAp.$8";

    String CONFIG_CHANNEL_ACCOUNT_PREFIX = "channel.";

    /*---------------微信openId-------------*/
    String AUTH_WX_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base";
    String TOKEN_WX_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

}