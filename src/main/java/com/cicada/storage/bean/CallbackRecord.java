package com.cicada.storage.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 回调记录
 *
 * @author jinxiao.wu 2019-01-09
 */
public class CallbackRecord implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 交易平台流水号
     */
    private String recordId;

    /**
     * 回调类型1、自动，2、渠道，3、任务
     */
    private Integer callbackType;

    /**
     * 交易状态0失败，1成功
     */
    private Integer status;

    /**
     * 回调状态0未结束，1结束
     */
    private Integer finish;

    /**
     * 渠道回调时间
     */
    private Date agentNotifyTime;

    /**
     * 渠道回调数据
     */
    private String agentNotifyData;

    /**
     * 渠道回调回复时间
     */
    private Date agentReturnTime;

    /**
     * 渠道回调回复数据
     */
    private String agentReturnData;

    /**
     * 回调数据
     */
    private String callbackData;

    /**
     * 签名key
     */
    private String signKey;

    /**
     * 回调记录创建时间
     */
    private Date createTime;

    /**
     * 回调地址
     */
    private String callbackUrl;


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

    public Integer getCallbackType() {
        return callbackType;
    }

    public void setCallbackType(Integer callbackType) {
        this.callbackType = callbackType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFinish() {
        return finish;
    }

    public void setFinish(Integer finish) {
        this.finish = finish;
    }

    public Date getAgentNotifyTime() {
        return agentNotifyTime;
    }

    public void setAgentNotifyTime(Date agentNotifyTime) {
        this.agentNotifyTime = agentNotifyTime;
    }

    public String getAgentNotifyData() {
        return agentNotifyData;
    }

    public void setAgentNotifyData(String agentNotifyData) {
        this.agentNotifyData = agentNotifyData;
    }

    public Date getAgentReturnTime() {
        return agentReturnTime;
    }

    public void setAgentReturnTime(Date agentReturnTime) {
        this.agentReturnTime = agentReturnTime;
    }

    public String getAgentReturnData() {
        return agentReturnData;
    }

    public void setAgentReturnData(String agentReturnData) {
        this.agentReturnData = agentReturnData;
    }

    public String getCallbackData() {
        return callbackData;
    }

    public void setCallbackData(String callbackData) {
        this.callbackData = callbackData;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

}