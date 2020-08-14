package com.cicada.core.enums;

import com.dotin.common.utils.StringUtils;

/**
 * 短信类型
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum SmsType implements Valuable {

    CONSUME("00", "消费短信"),
    BIND("01", "绑卡短信"),
    AUTH("02", "预授权短信");

    private String value;
    private String desc;

    SmsType(String value, String desc) {
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

    public static SmsType from(String type) {

        if (StringUtils.isEmpty(type)) {
            return null;
        }

        for (SmsType em : SmsType.values()) {
            if (StringUtils.equals(em.value(), type)) {
                return em;
            }
        }

        throw new IllegalArgumentException("Not found SmsType type by '" + type + "'!");
    }

}
