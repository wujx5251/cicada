package com.cicada.storage.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易记录（支付,退款记录）
 *
 * @author jinxiao.wu
 * @create 2018-12-24
 */
public class TradeRecord implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 交易平台流水号
     */
    private String recordId;

    /**
     * 业务单号
     */
    private String orderId;

    /**
     * 订单时间
     */
    private Date orderTime;

    /**
     * 订单标题
     */
    private String subject;

    /**
     * 订单描述
     */
    private String body;

    /**
     * 交易状态，0:失败，1：成功，2：待交易
     */
    private Integer status;

    /**
     * 交易金额
     */
    private Integer amount;

    /**
     * 退款总金额
     */
    private Integer refundAmount;

    /**
     * 货币
     */
    private String currency;

    /**
     * 绑卡id
     */
    private String bindId;

    /**
     * 回调通知地址
     */
    private String notifyUrl;

    /**
     * 交易完成跳转地址
     */
    private String returnUrl;

    /**
     * 回退地址
     */
    private String quitUrl;

    /**
     * 客户端ip
     */
    private String clientIp;

    /**
     * 交易类型1收款2付款3退款4撤销
     */
    private Integer tradeType;

    /**
     * 付款时间
     */
    private Date payTime;

    /**
     * 订单失效时间
     */
    private Date expireTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getQuitUrl() {
        return quitUrl;
    }

    public void setQuitUrl(String quitUrl) {
        this.quitUrl = quitUrl;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

}