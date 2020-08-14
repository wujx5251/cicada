package com.cicada.component.alipay.enums;

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
public enum AlipayStatus implements Valuable {
    WAIT("WAIT_BUYER_PAY", "交易创建，等待买家付款"),
    CLOSED("TRADE_CLOSED", "未付款交易超时关闭，或支付完成后全额退款"),
    SUCCESS("TRADE_SUCCESS", "交易支付成功"),
    FINISHED("TRADE_FINISHED", "交易结束，不可退款");


    private String value;
    private String desc;

    AlipayStatus(String value, String desc) {
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

    public static AlipayStatus from(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        for (AlipayStatus type : AlipayStatus.values()) {
            if (type.value().equals(value)) {
                return type;
            }
        }

        throw new IllegalTradingStatusException(value);
    }

}