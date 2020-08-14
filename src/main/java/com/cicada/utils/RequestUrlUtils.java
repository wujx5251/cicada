package com.cicada.utils;


import com.cicada.Config;
import com.cicada.core.bean.Application;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.ProductType;

/**
 * 获取请求/通知地址
 * Created by jinxiao.wu
 */
public class RequestUrlUtils {

    /**
     * 获取回调路径
     *
     * @param context 支付上下文
     * @return 回调路径
     */
    public static String getPayUrl(TradingContext context) {
        if (null == context.getProductType() || ProductType.NONE.value().equals(context.getProductType().value()))
            return null;
        StringBuilder builder = new StringBuilder();
        builder.append(Config.getDomain());
        builder.append('/');
        builder.append("pay");
        builder.append('/');
        builder.append(context.getChannel().value());
        builder.append('/');
        builder.append(context.getProductType().value());
        builder.append('/');
        builder.append(context.getRecordId());
        return builder.toString();
    }

    /**
     * 获取回调路径
     *
     * @param context 支付上下文
     * @return 回调路径
     */
    public static String getNotifyUrl(TradingContext context) {
        if (null == context.getProductType() || ProductType.NONE.value().equals(context.getProductType().value()))
            return null;
        StringBuilder builder = new StringBuilder();
        builder.append(Config.getDomain());
        builder.append('/');
        builder.append("notify");
        builder.append('/');
        builder.append(context.getChannel().value());
        builder.append('/');
        builder.append(context.getProductType().value());
        builder.append('/');
        builder.append(context.getRecordId());
        return builder.toString();
    }

    /**
     * 获取回调路径
     *
     * @param context 支付上下文
     * @return 跳转路径
     */
    public static String getReturnUrl(TradingContext context) {
        if (null == context.getProductType() || ProductType.NONE.value().equals(context.getProductType().value()))
            return null;
        StringBuilder builder = new StringBuilder();
        builder.append(Config.getDomain());
        builder.append('/');
        builder.append("direct");
        builder.append('/');
        builder.append(context.getChannel().value());
        builder.append('/');
        builder.append(context.getProductType().value());
        builder.append('/');
        builder.append(context.getRecordId());
        return builder.toString();
    }

    /**
     * 获取授权路径
     *
     * @param application
     * @return 跳转路径
     */
    public static String getAuthUrl(Application application) {
        StringBuilder builder = new StringBuilder();
        builder.append(Config.getDomain());
        builder.append('/');
        builder.append("auth");
        builder.append('/');
        builder.append(application.getChannel().value());
        builder.append('/');
        builder.append(application.getAppId());
        return builder.toString();
    }


}
