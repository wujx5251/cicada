package com.cicada.component.alipay.enums;

import com.cicada.core.enums.Valuable;

/**
 * 签名类型
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum AlipaySignType implements Valuable {

    RSA("RSA", "RSA"),
    RSA2("RSA2", "RSA2"),
    MD5("MD5", "MD5");

    private String value;
    private String desc;

    AlipaySignType(String value, String desc) {
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
