package com.cicada.component.wxpay.enums;

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
public enum WxpayStatus implements Valuable {
    WAIT("NOTPAY", "未支付"),
    CLOSED("CLOSED", "已关闭"),
    SUCCESS("SUCCESS", "支付成功"),
    REVOKED("REVOKED", "已撤销（刷卡支付）"),
    USERPAYING("USERPAYING", "用户支付中"),
    FAIL("PAYERROR", "支付失败(其他原因，如银行返回失败)"),
    REFUND("REFUND", "转入退款");

    private String value;
    private String desc;

    WxpayStatus(String value, String desc) {
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

    public static WxpayStatus from(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        for (WxpayStatus type : WxpayStatus.values()) {
            if (type.value().equals(value)) {
                return type;
            }
        }

        throw new IllegalTradingStatusException(value);
    }

}