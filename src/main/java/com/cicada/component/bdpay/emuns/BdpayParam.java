package com.cicada.component.bdpay.emuns;

import com.cicada.core.enums.Valuable;

/**
 * 百度钱包请求参数
 *
 * @author: WUJXIAO
 * @create: 2019-01-26 19:58
 * @version: 1.0
 **/
public enum BdpayParam implements Valuable {

    VERSION("version", "版本号"),
    CODE("service_code", "服务编号"),
    MCH_ID("sp_no", "商户号"),
    TRADE_NO("order_no", "交易流水号"),
    TRADE_TIME("order_create_time", "交易时间"),
    AMOUNT("total_amount", "交易金额"),
    CURRENCY("currency", "货币种类"),
    SUBJECT("goods_name", "交易名称"),
    BODY("goods_desc", "交易描述"),
    PAY_TYPE("pay_type", "支付方式类型"),
    BANK_ID("bank_no", "发卡行编号"),
    EXPIRE("expire_time", "订单失效时长"),
    NOTIFY_URL("return_url", "异步通知页面地址"),
    RETURN_URL("page_url", "支付成功跳转路径"),
    CHARSET("input_charset", "字符编码"),
    OUT_CHARSET("output_charset", "字符编码"),
    FORMAT("output_type", "响应数据的格式"),
    SIGN("sign", "交易信息签名"),
    SIGN_TYPE("sign_method", "签名方法"), ;


    private String value;
    private String desc;

    BdpayParam(String value, String desc) {
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