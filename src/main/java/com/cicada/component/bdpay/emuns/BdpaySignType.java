package com.cicada.component.bdpay.emuns;

import com.cicada.core.enums.Valuable;

/**
 * 签名类型
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum BdpaySignType implements Valuable {

    MD5("1", "MD5"),
    SHA1("2", "SHA-1");

    private String value;
    private String desc;

    BdpaySignType(String value, String desc) {
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
