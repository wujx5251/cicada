package com.cicada.core.bean;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.cicada.core.enums.SmsType;
import com.cicada.core.serializer.EunmSerializer;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易扩展信息
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public class TradingExtra implements Serializable {

    /**
     * 授权码
     */
    private String authCode;

    /**
     * 账单日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date billDate;

    /**
     * 开放授权码
     */
    private String openId;

    /**
     * 短信息类型 0默认消费短信，1绑卡短信，2预授权短信
     */
    @JSONField(deserializeUsing = EunmSerializer.class, serializeUsing = EunmSerializer.class)
    private SmsType smsType;

    public static TradingExtra build(String extra) {
        return JSON.parseObject(extra, TradingExtra.class);
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public SmsType getSmsType() {
        return smsType;
    }

    public void setSmsType(SmsType smsType) {
        this.smsType = smsType;
    }
}
