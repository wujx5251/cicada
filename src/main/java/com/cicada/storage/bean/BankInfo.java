package com.cicada.storage.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 银行支持列表
 *
 * @author jinxiao.wu
 * @create 2018-12-24
 */
public class BankInfo implements Serializable {

    /**
     * 数据库主键
     */
    private Long id;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行代码
     */
    private String bankCode;

    /**
     * 银行首字母
     */
    private String bankInitial;

    /**
     * 支持卡片类型 00：全部，01：贷记卡，02：借记卡
     */
    private String cardType;

    /**
     * 图标url
     */
    private String iconUrl;

    /**
     * 图标base64编码
     */
    private String iconBase64;

    /**
     * 优先级:1-9，数字大优先级高
     */
    private int level;

    /**
     * 所属渠道，见channel枚举
     */
    private String channel;

    /**
     * 支付类型支持，0：消费，:1付款，2：收款
     */
    private int payType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修建人
     */
    private String modifyBy;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankInitial() {
        return bankInitial;
    }

    public void setBankInitial(String bankInitial) {
        this.bankInitial = bankInitial;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getIconBase64() {
        return iconBase64;
    }

    public void setIconBase64(String iconBase64) {
        this.iconBase64 = iconBase64;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

}