package com.cicada.storage.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易明细
 *
 * @author jinxiao.wu
 * @create 2018-12-24
 */
public class RecordDetail implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 交易平台流水号
     */
    private String recordId;

    /**
     * 支付请求时间
     */
    private Date requestTime;

    /**
     * 支付响应时间
     */
    private Date responseTime;

    /**
     * 渠道请求时间
     */
    private Date channelRequestTime;

    /**
     * 渠道响应时间
     */
    private Date channelResponseTime;

    /**
     * 支付请求数据
     */
    private String requestData;

    /**
     * 支付响应数据
     */
    private String responseData;

    /**
     * 渠道请求数据
     */
    private String channelRequestData;

    /**
     * 渠道响应数据
     */
    private String channelResponseData;

    /**
     * 客户端保留数据
     */
    private String reqReserved;

    /**
     * 页面支付数据
     */
    private String pagePayData;

    /**
     * 超时时间
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

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public Date getChannelRequestTime() {
        return channelRequestTime;
    }

    public void setChannelRequestTime(Date channelRequestTime) {
        this.channelRequestTime = channelRequestTime;
    }

    public Date getChannelResponseTime() {
        return channelResponseTime;
    }

    public void setChannelResponseTime(Date channelResponseTime) {
        this.channelResponseTime = channelResponseTime;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getChannelRequestData() {
        return channelRequestData;
    }

    public void setChannelRequestData(String channelRequestData) {
        this.channelRequestData = channelRequestData;
    }

    public String getChannelResponseData() {
        return channelResponseData;
    }

    public void setChannelResponseData(String channelResponseData) {
        this.channelResponseData = channelResponseData;
    }

    public String getReqReserved() {
        return reqReserved;
    }

    public void setReqReserved(String reqReserved) {
        this.reqReserved = reqReserved;
    }

    public String getPagePayData() {
        return pagePayData;
    }

    public void setPagePayData(String pagePayData) {
        this.pagePayData = pagePayData;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}