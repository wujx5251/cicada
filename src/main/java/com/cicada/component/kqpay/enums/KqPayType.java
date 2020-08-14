package com.cicada.component.kqpay.enums;

import com.cicada.core.enums.Valuable;
import com.cicada.core.exception.business.CardTypeNotSupportException;
import com.dotin.common.utils.StringUtils;

/**
 * 快钱付款类型
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum KqPayType implements Valuable {

    WEB("10", "银行卡网银支付"),
    WEB_CREDIT("10-1", "借记卡网银支付"),
    WEB_DEBIT("10-2", "贷记卡网银支付"),
    QUICK("21", "快捷支付"),
    QUICK_CREDIT("21-1", "借记卡快捷"),
    QUICK_DEBIT("21-2", "贷记卡快捷");

    private String value;
    private String desc;

    KqPayType(String value, String desc) {
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


    public static KqPayType from(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        for (KqPayType type : KqPayType.values()) {
            if (type.value().equals(value)) {
                return type;
            }
        }

        throw new CardTypeNotSupportException(value);
    }
}
