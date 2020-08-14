package com.cicada.core.enums;

import com.dotin.common.utils.NumberUtils;

/**
 * 回调类型
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum CallbackType implements Valuable {

    ACTIVE(1, "主动"),
    CHANNEL(2, "渠道"),
    AUTO(3, "自动任务"),
    MANUAL(4, "手动");

    private int value;
    private String desc;

    CallbackType(int value, String desc) {
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

    public static CallbackType from(Integer type) {

        if (null == type) {
            return null;
        }

        for (CallbackType em : CallbackType.values()) {
            if (NumberUtils.isEquals(em.value, type)) {
                return em;
            }
        }

        throw new IllegalArgumentException("Not found callback type type by '" + type + "'!");
    }

}
