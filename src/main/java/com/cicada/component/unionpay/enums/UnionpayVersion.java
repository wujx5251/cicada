package com.cicada.component.unionpay.enums;

import com.cicada.core.enums.Valuable;

/**
 * 协议版本类型
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum UnionpayVersion implements Valuable {

    V1("1.0.0", "SHA"),
    V5("5.0.0", "SHA"),
    V5_0_1("5.0.1", "SHA"),
    V5_1_0("5.1.0", "SHA-256");


    private String version;
    private String desc;

    UnionpayVersion(String version, String desc) {
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
