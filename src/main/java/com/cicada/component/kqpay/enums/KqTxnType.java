package com.cicada.component.kqpay.enums;

import com.cicada.core.enums.Valuable;
import com.cicada.core.exception.business.NotSupportException;
import com.dotin.common.utils.StringUtils;

/**
 * 快钱交易类型
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum KqTxnType implements Valuable {

    PUR("PUR", "消费交易"),
    INP("INP", "分期消费交易"),
    PRE("PRE", "预授权交易"),
    CFM("CFM", "预授权完成交易"),
    VTX("VTX", "撤销交易"),
    RFD("RFD", "退货交易"),
    CIV("CIV", "卡信息验证交易"),
    RNA("RNA", "实名验证交易");

    private String value;
    private String desc;

    KqTxnType(String value, String desc) {
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


    public static KqTxnType from(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        for (KqTxnType type : KqTxnType.values()) {
            if (type.value().equals(value)) {
                return type;
            }
        }

        throw new NotSupportException(value);
    }
}
