package com.cicada.storage.bean;

import java.io.Serializable;
import java.util.Date;

/**
*  回调记录详情
* @author jinxiao.wu 2019-01-09
*/
public class CallbackDetail implements Serializable {

    /**
    * id
    */
    private Long id;

    /**
    * 回调记录id
    */
    private Long callbackId;

    /**
    * 回调时间
    */
    private Date callbackTime;

    /**
    * 响应时间
    */
    private Date callbackReturnTime;

    /**
    * 响应内容
    */
    private String callbackReturnData;

    /**
    * 回调状态0未结束，1结束
    */
    private Integer finish;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(Long callbackId) {
        this.callbackId = callbackId;
    }

    public Date getCallbackTime() {
        return callbackTime;
    }

    public void setCallbackTime(Date callbackTime) {
        this.callbackTime = callbackTime;
    }

    public Date getCallbackReturnTime() {
        return callbackReturnTime;
    }

    public void setCallbackReturnTime(Date callbackReturnTime) {
        this.callbackReturnTime = callbackReturnTime;
    }

    public String getCallbackReturnData() {
        return callbackReturnData;
    }

    public void setCallbackReturnData(String callbackReturnData) {
        this.callbackReturnData = callbackReturnData;
    }

    public Integer getFinish() {
        return finish;
    }

    public void setFinish(Integer finish) {
        this.finish = finish;
    }

}