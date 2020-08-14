package com.cicada.core.enums;

import com.cicada.core.exception.business.NotSupportException;
import com.dotin.common.utils.NumberUtils;

public enum TradingStatus implements Valuable {

    FAIL(0, "交易失败"),
    SUCCESS(1, "交易成功"),
    PENDING(2, "待交易"),
    CLOSED(3, "交易关闭"),
    FINSHD(4, "交易完成");

    private int value;
    private String desc;

    TradingStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer value() {
        return value;
    }

    @Override
    public String desc() {
        return desc;
    }

    public static TradingStatus from(Integer value) {
        if (null == value) {
            return null;
        }

        for (TradingStatus item : TradingStatus.values()) {
            if (NumberUtils.isEquals(item.value(), value)) {
                return item;
            }
        }

        throw new NotSupportException("the trading status：" + value + " not support!");
    }
}
