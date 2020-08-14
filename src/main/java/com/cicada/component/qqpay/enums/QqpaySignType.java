package com.cicada.component.qqpay.enums;

import com.cicada.core.enums.Valuable;

/**
 * 签名类型
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum QqpaySignType implements Valuable {

    MD5("MD5", "md5"),
    HMAC_SHA256("HMAC-SHA256", "HmacSha256");

    private String value;
    private String desc;

    QqpaySignType(String value, String desc) {
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

}
