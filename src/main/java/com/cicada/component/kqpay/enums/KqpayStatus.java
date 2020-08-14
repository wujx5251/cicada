package com.cicada.component.kqpay.enums;

import com.cicada.core.enums.Valuable;
import com.cicada.core.exception.business.IllegalTradingStatusException;
import com.dotin.common.utils.StringUtils;

/**
 * 交易状态
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public enum KqpayStatus implements Valuable {
    SUCCESS("S", "交易成功"),
    FAIL("F", "交易失败"),
    WAIT("P", "交易挂起"),
    CANCEL("R", "交易冲正"),
    WAIT_D("D", "交易挂起");


    private String value;
    private String desc;

    KqpayStatus(String value, String desc) {
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

    public static KqpayStatus from(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        for (KqpayStatus type : KqpayStatus.values()) {
            if (type.value().equals(value)) {
                return type;
            }
        }

        throw new IllegalTradingStatusException(value);
    }

}