package com.cicada.core.bean;


import com.cicada.core.enums.TradingStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易结果
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public class TradingResult<T> implements Serializable {

    /**
     * 交易流水号
     */
    private String recordId;
    /**
     * 三方支付流水号
     */
    private String replyId;
    /**
     * 交易金额
     */
    private Integer amount;
    /**
     * 交易状态
     */
    private TradingStatus status;
    /**
     * 回复时间
     */
    private Date rspTime;
    /**
     * 错误信息
     */
    private String errMsg;
    /**
     * 是否进行网络重试
     */
    private boolean retry;
    /**
     * 交易数据
     */
    private T data;

    public TradingResult() {
    }

    public TradingResult(TradingStatus status) {
        this.status = status;
    }

    public TradingResult setRecordId(String recordId) {
        this.recordId = recordId;
        return this;
    }

    public TradingResult setReplyId(String replyId) {
        this.replyId = replyId;
        return this;
    }

    public TradingResult setStatus(TradingStatus status) {
        this.status = status;
        return this;
    }

    public TradingResult setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }

    public TradingResult setRetry(boolean retry) {
        this.retry = retry;
        return this;
    }

    public TradingResult setData(T data) {
        this.data = data;
        return this;
    }

    public TradingResult setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public Date getRspTime() {
        return rspTime;
    }

    public TradingResult setRspTime(Date rspTime) {
        this.rspTime = rspTime;
        return this;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getReplyId() {
        return replyId;
    }

    public TradingStatus getStatus() {
        return status;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public boolean isRetry() {
        return retry;
    }

    public T getData() {
        return data;
    }
}
