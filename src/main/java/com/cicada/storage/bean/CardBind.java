package com.cicada.storage.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 绑卡列表
 *
 * @author jinxiao.wu
 * @create 2018-12-24
 */
public class CardBind implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 请求流水号
     */
    private String recordId;

    /**
     * 绑定id
     */
    private String bindId;

    /**
     * 卡信息id
     */
    private Long cardId;

    /**
     * 所属商户
     */
    private String mchId;

    /**
     * 所属商户
     */
    private String appId;

    /**
     * 所属渠道：3：银联，4：快钱
     */
    private String channel;

    /**
     * 渠道绑卡标记
     */
    private String token;

    /**
     * 短卡号，前六后四中间*代替
     */
    private String shortCardNo;

    /**
     * 加密卡号MD5
     */
    private String encCardNo;

    /**
     * 卡类型
     */
    private String cardType;

    /**
     * 状态 1绑定，2待绑定，0已解绑
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 绑定时间
     */
    private Date bindTime;

    /**
     * 刷新时间
     */
    private Date lastRefreshTime;


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

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getShortCardNo() {
        return shortCardNo;
    }

    public void setShortCardNo(String shortCardNo) {
        this.shortCardNo = shortCardNo;
    }

    public String getEncCardNo() {
        return encCardNo;
    }

    public void setEncCardNo(String encCardNo) {
        this.encCardNo = encCardNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getBindTime() {
        return bindTime;
    }

    public void setBindTime(Date bindTime) {
        this.bindTime = bindTime;
    }

    public Date getLastRefreshTime() {
        return lastRefreshTime;
    }

    public void setLastRefreshTime(Date lastRefreshTime) {
        this.lastRefreshTime = lastRefreshTime;
    }

}