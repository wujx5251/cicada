package com.cicada.component.kqpay.enums;

import com.cicada.core.enums.Valuable;

/**
 * 快钱支付接口
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public enum KqpayAction implements Valuable {
    WEB_PAY("/gateway/recvMerchantInfoAction.htm", "web支付"),
    WAP_PAY("/mobilegateway/recvMerchantInfoAction.htm", "h5支付"),
    CARD_INFO("/cnp/query_cardinfo", "卡信息查询"),
    QUERY_STATUS("/cnp/query_txn", "交易状态查询"),
    CANCEL("/cnp/void", "交易撤销"),
    REFUND("/cnp/refund", "交易退货"),
    SMS_SENDD("/cnp/getDynNum", "发送短信"),
    OPEN("/cnp/ind_auth", "卡信息验证，开通绑卡"),
    OPEN_CONFIRM("/cnp/ind_auth_verify", "卡信息验证-动态码验证，开通绑卡"),
    OPEN_QUERY("/cnp/pci_query", "PCI 数据查询"),
    OPEN_REMOVE("/cnp/pci_del", "PCI 数据删除"),
    CONSUME("/cnp/purchase", "消费交易"),
    AUTH("/cnp/preauth", "预授权"),
    AUTH_COMPLETE("/cnp/confirm", "预授权完成"),
    ;

    private String value;
    private String desc;

    KqpayAction(String value, String desc) {
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