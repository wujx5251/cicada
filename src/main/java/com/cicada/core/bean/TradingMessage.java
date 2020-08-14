package com.cicada.core.bean;

import com.alibaba.fastjson.JSON;
import com.cicada.core.serializer.SecuritySerializer;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 交易报文
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public class TradingMessage implements Serializable {

    //客户端发起交易请求的时间和报文
    private Date requestTime;
    private String requestData;


    //平台发往第三方的时间和报文
    private Date agentRequestTime;
    private String agentRequestData;

    //第三方回复平台的时间和报文
    private Date agentResponseTime;
    private String agentResponseData;

    //回复客户端的时间和报文
    private Date responseTime;
    private String responseData;
    private String reqReserved;

    //支付扩展数据
    private String pagePayData;
    private String extraData;

    private final Map<String, Object> origin;

    public TradingMessage(Map<String, Object> params) {
        origin = params;
        if (null != params) {
            requestData = JSON.toJSONString(params, SecuritySerializer.instance());
        }
    }

    public Map<String, Object> getOrigin() {
        return origin;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getRequestData() {
        return requestData;
    }

    public Date getAgentRequestTime() {
        return agentRequestTime;
    }

    public void setAgentRequestTime(Date agentRequestTime) {
        this.agentRequestTime = agentRequestTime;
    }

    public String getAgentRequestData() {
        return agentRequestData;
    }

    public void setAgentRequestData(String agentRequestData) {
        this.agentRequestData = agentRequestData;
    }

    public Date getAgentResponseTime() {
        return agentResponseTime;
    }

    public void setAgentResponseTime(Date agentResponseTime) {
        this.agentResponseTime = agentResponseTime;
    }

    public String getAgentResponseData() {
        return agentResponseData;
    }

    public void setAgentResponseData(String agentResponseData) {
        this.agentResponseData = agentResponseData;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getReqReserved() {
        return reqReserved;
    }

    public void setReqReserved(String reqReserved) {
        this.reqReserved = reqReserved;
    }

    public String getPagePayData() {
        return pagePayData;
    }

    public void setPagePayData(String pagePayData) {
        this.pagePayData = pagePayData;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }
}
