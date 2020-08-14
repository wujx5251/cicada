package com.cicada.component.kqpay.enums;

import com.cicada.core.enums.Valuable;
import com.cicada.core.exception.business.NotSupportException;
import com.dotin.common.utils.StringUtils;

/**
 * 快钱数据根节点名称
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum KqNodeType implements Valuable {

    QRY_CARD("QryCardContent", "查询卡信息"),
    IND_AUTH("indAuthContent", "绑卡授权短信"),
    IND_AUTH_VERIFY("indAuthDynVerifyContent", "绑卡确认"),
    PCI_QRY("PciQueryContent", "查询绑卡"),
    PCI_DEL("PciDeleteContent", "移除绑卡"),
    QRY_TXN("QryTxnMsgContent", "查询交易信息"),
    TXN_MSG("TxnMsgContent", "交易信息"),
    GET_DYN("GetDynNumContent", "发送短信");

    private String value;
    private String desc;

    KqNodeType(String value, String desc) {
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


    public static KqNodeType from(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        for (KqNodeType type : KqNodeType.values()) {
            if (type.value().equals(value)) {
                return type;
            }
        }

        throw new NotSupportException("the kq protocol node：" + value + " not support!");
    }
}
