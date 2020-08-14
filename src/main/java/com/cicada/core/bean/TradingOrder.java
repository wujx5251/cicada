package com.cicada.core.bean;


import com.cicada.core.enums.Currency;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易订单
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public class TradingOrder implements Serializable {

    /**
     * 业务订单ID
     */
    private String orderId;

    /**
     * 业务订单标题
     */
    private String subject;

    /**
     * 业务订单详情
     */
    private String body;

    private Integer totalAmount;

    /**
     * 业务订单金额；
     * 整数，实际订单金额乘以100，例如实际订单金额为13.14元，则为1314
     */
    private Integer amount;

    /**
     * 退货总金额
     */
    private Integer refundAmount;

    /**
     * 货币,默认cny
     */
    private Currency currency = Currency.CNY;

    /**
     * 订单时间
     */
    private Date orderTime;

    /**
     * 描述信息
     */
    private String desc;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Integer refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
