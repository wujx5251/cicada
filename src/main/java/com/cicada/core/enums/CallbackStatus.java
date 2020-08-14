package com.cicada.core.enums;

import com.dotin.common.utils.NumberUtils;

/**
 * 回调状态类型
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum CallbackStatus implements Valuable {

    SUCCESS(1, "成功"),
    PENDING(2, "未受理"),
    CANCEL(3, "取消执行"),
    ONCE(4, "执行一次");

    private int value;
    private String desc;

    CallbackStatus(int value, String desc) {
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

    public static CallbackStatus from(Integer type) {

        if (null == type) {
            return null;
        }

        for (CallbackStatus em : CallbackStatus.values()) {
            if (NumberUtils.isEquals(em.value, type)) {
                return em;
            }
        }

        throw new IllegalArgumentException("Not found callback type type by '" + type + "'!");
    }

}
