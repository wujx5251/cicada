package com.cicada.component.jdpay.bean;

import com.cicada.core.bean.Response;

import java.util.Map;

/**
 * 京东支付返回结果
 *
 * @author: WUJXIAO
 * @create: 2019-01-25 08:58
 * @version: 1.0
 **/
public class JdResponse implements Response {

    public final static String SUCCESS = "000000";
    public final static String OK = "2";
    public final static String[] PENDING = {"0", "1"};

    private String code;
    private String msg;

    private String qrCode;
    private String replyId;//流水号
    private String status;//交易状态


    private JdResponse() {
    }

    public static JdResponse from(Map<String, String> params) {
        JdResponse rsp = new JdResponse();

        Object result = params.get("result");
        if (result instanceof Map) {
            rsp.code = ((Map<String, String>) result).get("code");
            rsp.msg = ((Map<String, String>) result).get("desc");
        }


        rsp.replyId = params.get("orderId");
        rsp.qrCode = params.get("qrCode");
        rsp.status = params.get("status");
        return rsp;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getQrCode() {
        return qrCode;
    }

    @Override
    public String getReplyId() {
        return replyId;
    }

    public String getStatus() {
        return status;
    }


    @Override
    public String getExtra() {
        final StringBuilder sb = new StringBuilder("{");
        if (null != qrCode)
            sb.append("\"qrCode\":\"").append(qrCode).append('\"');
        sb.append('}');
        return sb.length() == 2 ? "" : sb.indexOf(",", 1) == 1 ? sb.deleteCharAt(1).toString() : sb.toString();
    }
}