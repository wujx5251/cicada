package com.cicada.core.bean;

import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ProductType;

import java.io.Serializable;

/**
 * 交易上下文
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public class TradingContext implements Serializable {

    /**
     * 接口名称
     */
    private String method;

    /**
     * 接口版本
     */
    private String version;

    /**
     * 交易流水ID
     */
    private String recordId;

    /**
     * 三方渠道流水号
     */
    private String replyId;

    /**
     * 商户应用信息
     */
    private Application application;

    /**
     * 订单信息
     */
    private TradingOrder order;

    /**
     * 交易报文
     */
    private TradingMessage message;

    /**
     * 支付银行卡信息
     */
    private TradingCard card;

    /**
     * 交易渠道账户配置
     */
    private AccountConfig config;

    /**
     * 交易渠道
     */
    private Channel channel;

    /**
     * 渠道产品
     */
    private ProductType productType;

    /**
     * 附加参数,特定渠道发起时额外参数
     */
    private TradingExtra extra;

    /**
     * 交易结果
     */
    private TradingResult result;

    /**
     * 客户端IP地址
     */
    private String clientIp;

    /**
     * 客户端设备
     */
    private String device;

    /**
     * 前台通知接收url
     */
    private String returnUrl;

    /**
     * 后台通知接收url
     */
    private String notifyUrl;

    /**
     * 前台取消支付url
     */
    private String quitUrl;

    /**
     * 创建时间
     */
    private Long createTime;


    /**
     * 目标支付上下文对象
     */
    private TradingContext target;

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

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public TradingCard getCard() {
        return card;
    }

    public void setCard(TradingCard card) {
        this.card = card;
    }

    public TradingOrder getOrder() {
        return order;
    }

    public void setOrder(TradingOrder order) {
        this.order = order;
    }

    public TradingMessage getMessage() {
        return message;
    }

    public void setMessage(TradingMessage message) {
        this.message = message;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getQuitUrl() {
        return quitUrl;
    }

    public void setQuitUrl(String quitUrl) {
        this.quitUrl = quitUrl;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public TradingExtra getExtra() {
        return extra;
    }

    public void setExtra(TradingExtra extra) {
        this.extra = extra;
    }

    public Long getCreateTime() {
        return createTime == null ? createTime = System.currentTimeMillis() : createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public TradingResult getResult() {
        return result;
    }

    public void setResult(TradingResult result) {
        this.result = result;
    }

    public TradingContext getTarget() {
        return target;
    }

    public void setTarget(TradingContext target) {
        this.target = target;
    }

    public <T extends AccountConfig> T getConfig() {
        return (T) config;
    }

    public <T extends AccountConfig> void setConfig(T config) {
        this.config = config;
    }

}