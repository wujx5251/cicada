package com.cicada.storage.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 银行卡信息
 *
 * @author jinxiao.wu
 * @create 2018-12-24
 */
public class CardInfo implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 请求流水号
     */
    private String recordId;

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 持卡人姓名
     */
    private String holderName;

    /**
     * 发卡行简码
     */
    private String bankCode;

    /**
     * 发卡行名称
     */
    private String bankName;

    /**
     * 银行卡类型：01:信用卡，02:借记卡
     */
    private String cardType;

    /**
     * 过期年
     */
    private String expiredYear;

    /**
     * 过期月
     */
    private String expiredMonth;

    /**
     * 证件类型：01：身份证 02：军官证 03：护照 04：回乡证 05：台胞证 06：警官证 07：士兵证 99：其它证件
     */
    private String certificationType;

    /**
     * 证件编号
     */
    private String certificationNo;

    /**
     * cvv
     */
    private String cvv2;

    /**
     * 银行卡绑定的手机号
     */
    private String mobileNo;

    /**
     * 盐
     */
    private String salt;

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

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getExpiredYear() {
        return expiredYear;
    }

    public void setExpiredYear(String expiredYear) {
        this.expiredYear = expiredYear;
    }

    public String getExpiredMonth() {
        return expiredMonth;
    }

    public void setExpiredMonth(String expiredMonth) {
        this.expiredMonth = expiredMonth;
    }

    public String getCertificationType() {
        return certificationType;
    }

    public void setCertificationType(String certificationType) {
        this.certificationType = certificationType;
    }

    public String getCertificationNo() {
        return certificationNo;
    }

    public void setCertificationNo(String certificationNo) {
        this.certificationNo = certificationNo;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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