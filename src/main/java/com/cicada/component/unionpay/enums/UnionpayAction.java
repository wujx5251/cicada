package com.cicada.component.unionpay.enums;

import com.cicada.core.enums.Valuable;

/**
 * 银联支付接口
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public enum UnionpayAction implements Valuable {
    FRONT_REQ("/frontTransReq.do", "前台支付"),
    APP_REQ("/appTransReq.do", "APP支付"),
    BACK_REQ("/backTransReq.do", "后台支付");

    private String value;
    private String desc;

    UnionpayAction(String value, String desc) {
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