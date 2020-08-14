package com.cicada.core.enums;


import com.cicada.core.exception.business.ChannelNotSupportException;
import com.dotin.common.utils.StringUtils;

/**
 * 支付渠道枚举
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum Channel implements Valuable {

    ALIPAY("alipay", "支付宝"),
    WXPAY("wxpay", "微信支付"),
    QQPAY("qqpay", "qq钱包"),
    JDPAY("jdpay", "京东钱包"),
    BDPAY("bdpay", "百度钱包"),
    UNIONPAY("unionpay", "银联支付"),
    KQPAY("kqpay", "快钱支付");

    private String value;
    private String desc;

    Channel(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String desc() {
        return desc;
    }

    public static Channel from(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        for (Channel item : Channel.values()) {
            if (StringUtils.equals(item.value(), value)) {
                return item;
            }
        }

        throw new ChannelNotSupportException(value);
    }

    public static String toJson() {
        final StringBuilder sb = new StringBuilder("[");
        for (Channel i : Channel.values()) {
            sb.append(i.toString()).append(",");
        }
        sb.setCharAt(sb.length() - 1, ']');
        return sb.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"code\":\"").
                append(value);
        sb.append("\",\"name\":\"").
                append(desc).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
