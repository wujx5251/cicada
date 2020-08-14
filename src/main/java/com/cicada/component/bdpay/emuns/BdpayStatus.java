package com.cicada.component.bdpay.emuns;

import com.cicada.core.enums.Valuable;

/**
 * 百度钱包服务编号
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public enum BdpayStatus implements Valuable {

    SUCCESS("1", "[1]支付成功"),
    PENDING("2", "[2]等待支付"),
    REFUND("3", "[3]退款成功"),
    FAIL("5", "[5]支付失败"),
    EMPTY("1002", "[1002]查询结果为空"),
    PARAM("5801", "[5801]缺少请求参数"),
    LOSE("5802", "[5802]请求参数非法"),
    SIGN_TYPE("5803", "[5803]不支持的签名算法"),
    SIGN("5804", "[5804]验签失败"),
    INNER("5806", "[5806]服务器内部错误"),;

    private String value;
    private String desc;

    BdpayStatus(String value, String desc) {
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

    public static BdpayStatus from(String value) {
        if (null == value) {
            return null;
        }
        for (BdpayStatus em : BdpayStatus.values()) {
            if (em.value.equals(value)) {
                return em;
            }
        }
        return null;
    }

}