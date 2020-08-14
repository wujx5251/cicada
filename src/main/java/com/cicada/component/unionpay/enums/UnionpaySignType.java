package com.cicada.component.unionpay.enums;

import com.cicada.core.enums.Valuable;

/**
 * 签名类型
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum UnionpaySignType implements Valuable {

    SM3("12", "SM3"),
    RSA("01", "RSA"),
    SHA256("11", "SHA-256");

    private String version;
    private String desc;

    UnionpaySignType(String version, String desc) {
        this.version = version;
        this.desc = desc;
    }

    @Override
    public String value() {
        return version;
    }

    @Override
    public String desc() {
        return desc;
    }

}
