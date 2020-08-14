package com.cicada.core.enums;

import com.dotin.common.utils.StringUtils;

/**
 * 支付产品类型
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum ProductType implements Valuable {

    APP("app", TradeType.FETCH, "APP支付"),
    WEB("web", TradeType.FETCH, "WEB支付"),
    H5("wap", TradeType.FETCH, "H5支付"),
    JSAPI("js", TradeType.FETCH, "JSAPI支付"),
    APPLET("applet", TradeType.FETCH, "小程序支付"),
    QRCODE("qr", TradeType.FETCH, "付款码支付"),
    SCAN("scan", TradeType.FETCH, "扫描支付"),
    QUICK("quick", TradeType.FETCH, "快捷支付"),
    AUTH("auth", TradeType.FETCH, "预授权"),
    AUTH_CONFIRM("auth_cfm", TradeType.FETCH, "预授权完成"),
    REFUND("refund", TradeType.REFUND, "支付退货"),
    CANCEL("cancel", TradeType.CANCEL, "支付撤销"),
    QUICK_PRE("none", null, "快捷支付预处理，发送短信，绑卡，查询等"),
    NONE("none", null, "未知，依赖父支付记录");

    /**
     * 支付接口，渠道配置使用该值
     * 交易记录使用name进行标识交易类型
     */
    private final String value;
    private final TradeType tradeType;
    private final String desc;

    ProductType(String value, TradeType type, String desc) {
        this.value = value;
        this.tradeType = type;
        this.desc = desc;
    }

    @Override
    public String value() {
        return value;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    @Override
    public String desc() {
        return desc;
    }

    public static ProductType from(String type) {
        if (StringUtils.isEmpty(type)) {
            return null;
        }
        for (ProductType em : ProductType.values()) {
            if (StringUtils.equals(em.name().toLowerCase(), type)) {
                return em;
            }
        }
        return null;
    }

    public static ProductType fromByValue(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (ProductType em : ProductType.values()) {
            if (StringUtils.equals(em.value, value)) {
                return em;
            }
        }
        return null;
    }

    public static String toJson() {
        final StringBuilder sb = new StringBuilder("[");
        for (ProductType i : ProductType.values()) {
            if (TradeType.FETCH.equals(i.tradeType)) {
                sb.append(i.toString()).append(",");
            }
        }
        sb.setCharAt(sb.length() - 1, ']');
        return sb.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"code\":\"").
                append(name().toLowerCase());
        sb.append("\",\"name\":\"").
                append(desc).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
