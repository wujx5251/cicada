package com.cicada.component.unionpay.enums;

import com.cicada.core.enums.SmsType;
import com.cicada.core.enums.Valuable;
import com.cicada.core.exception.business.NotSupportException;

/**
 * 银联短信类型
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public enum UnionpaySmsType implements Valuable {

    OPEN_CARD("00", SmsType.BIND),
    AUTH("04", SmsType.AUTH),
    CONSUME("02", SmsType.CONSUME);

    private String value;
    private SmsType desc;

    UnionpaySmsType(String value, SmsType desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String desc() {
        return desc.desc();
    }

    public static UnionpaySmsType from(SmsType name) {
        for (UnionpaySmsType sm : UnionpaySmsType.values()) {
            if (sm.desc.equals(name)) {
                return sm;
            }
        }
        throw new NotSupportException("the sms type：" + name.value() + " not support!");
    }
}