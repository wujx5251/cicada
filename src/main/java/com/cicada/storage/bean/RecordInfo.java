package com.cicada.storage.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易流水
 *
 * @author jinxiao.wu
 * @create 2018-12-24
 */
public class RecordInfo implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 交易平台流水号
     */
    private String recordId;

    /**
     * 三方流水号
     */
    private String replyId;

    /**
     * 目标交易流水号
     */
    private String targetRecordId;

    /**
     * 交易金额
     */
    private Integer amount;

    /**
     * api名称
     */
    private String method;

    /**
     * api版本
     */
    private String version;

    /**
     * 商户id
     */
    private String mchId;

    /**
     * 应用id
     */
    private String appId;

    /**
     * 渠道商户id
     */
    private String channelMchId;

    /**
     * 渠道应用id
     */
    private String channelAppId;

    /**
     * 所属渠道，见channel枚举
     */
    private String channel;

    /**
     * 所属支付产品，见producttype枚举
     */
    private String product;

    /**
     * 无卡消费所需的绑卡id
     */
    private String bindId;

    /**
     * 结果编码
     */
    private Integer resultStatus;

    /**
     * 结果响应信息
     */
    private String resultMsg;

    /**
     * 请求时间
     */
    private Date requestTime;

    /**
     * 响应时间
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
     * 扩展数据
     */
    private String extraData;

    private String serverIp;

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

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTargetRecordId() {
        return targetRecordId;
    }

    public void setTargetRecordId(String targetRecordId) {
        this.targetRecordId = targetRecordId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getChannelMchId() {
        return channelMchId;
    }

    public void setChannelMchId(String channelMchId) {
        this.channelMchId = channelMchId;
    }

    public String getChannelAppId() {
        return channelAppId;
    }

    public void setChannelAppId(String channelAppId) {
        this.channelAppId = channelAppId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public Integer getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
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

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }
}