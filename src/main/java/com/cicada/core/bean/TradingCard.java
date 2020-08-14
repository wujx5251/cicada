package com.cicada.core.bean;

import com.cicada.core.enums.CardType;
import com.cicada.core.enums.CertificationType;
import com.dotin.common.utils.DateUtils;
import com.dotin.common.utils.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易卡信息
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public class TradingCard implements Serializable {

    private Long id;

    /**
     * 银行卡类型
     */
    private CardType type;

    /**
     * 绑卡标记
     */
    private String bindId;

    /**
     * 渠道绑卡标记
     */
    private String token;

    /**
     * 银行卡号
     */
    private String no;

    /**
     * 银行卡过期年份
     */
    private String expireYear;

    /**
     * 银行卡过期月份
     */
    private String expireMonth;

    /**
     * 银行卡 CVV 码
     */
    private String cvv;

    /**
     * 银行卡预留手机号
     */
    private String mobileNo;

    /**
     * 银行卡持有者证件类型
     */
    private transient CertificationType certificationType = CertificationType.IDENTIFICATION;

    /**
     * 银行卡持有者证件号码
     */
    private String certificationNo;

    /**
     * 银行卡持有者姓名
     */
    private String holderName;

    /**
     * 银行卡机构码，银行卡银行简码
     */
    private String orgCode;

    /**
     * 银行卡机构码，银行卡银行名称
     */
    private String orgName;

    /**
     * 持卡人国别
     */
    private String nationCode;

    /**
     * 绑卡token刷新日期
     */
    private Date refreshDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getExpireYear() {
        return expireYear;
    }

    public void setExpireYear(String expireYear) {
        this.expireYear = expireYear;
    }

    public String getExpireMonth() {
        return expireMonth;
    }

    public void setExpireMonth(String expireMonth) {
        this.expireMonth = expireMonth;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public CertificationType getCertificationType() {
        return certificationType;
    }

    public void setCertificationType(CertificationType certificationType) {
        this.certificationType = certificationType;
    }

    public String getCertificationNo() {
        return certificationNo;
    }

    public void setCertificationNo(String certificationNo) {
        this.certificationNo = certificationNo;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    public Date getRefreshDate() {
        if (null == refreshDate) {
            String expire = getExpire();
            if (null != expire) {
                return DateUtils.parseDate(expire, "YYMM");
            }
        }
        return refreshDate;
    }

    public void setRefreshDate(Date refreshDate) {
        this.refreshDate = refreshDate;
    }

    /**
     * 有效期
     *
     * @return YYMM
     */
    public String getExpire() {
        return getExpire(true);
    }

    /**
     * 有效期
     *
     * @param az 是否正序 true YYMM false MMYY
     * @return
     */
    public String getExpire(boolean az) {
        if (StringUtils.isNotEmpty(expireYear) && StringUtils.isNotEmpty(expireMonth)) {
            if (az) {
                return expireYear + expireMonth;
            } else {
                return expireMonth + expireYear;
            }
        }
        return null;
    }

    /**
     * <p>因为统一初始化的逻辑，会导致银行卡对象存在，但数据全是空的情况；</p>
     * <p>如果卡号为空，则认为为空！</p>
     *
     * @return 是否为空
     */
    public boolean isEmpty() {
        return StringUtils.isEmpty(no);
    }

}
