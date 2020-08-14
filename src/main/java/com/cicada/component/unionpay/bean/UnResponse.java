package com.cicada.component.unionpay.bean;

import com.cicada.core.bean.Response;

import java.util.Map;

/**
 * 银联返回结果
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public class UnResponse implements Response {

    public final static String SUCCESS = "00";
    public final static String[] PENDING = {"A6", "03", "04", "05"};

    private String code;
    private String msg;
    private String subCode;
    private String subMsg;

    private String tnCode;
    private String replyId;//银联流水号
    private String traceNo;//系统跟踪号
    private String token;//绑卡token
    private String fileContent;//文件内容
    private String qrCode;

    private UnResponse() {
    }

    public static UnResponse from(Map<String, String> params) {
        UnResponse rsp = new UnResponse();
        rsp.code = params.get("respCode");
        rsp.msg = params.get("respMsg");
        rsp.subCode = params.get("origRespCode");
        rsp.subMsg = params.get("origRespMsg");

        rsp.tnCode = params.get("tn");
        rsp.replyId = params.get("queryId");
        rsp.traceNo = params.get("traceNo");
        rsp.fileContent = params.get("fileContent");
        rsp.qrCode = params.get("qrCode");
        rsp.token = params.get("token");
        return rsp;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getSubCode() {
        return subCode;
    }

    public String getSubMsg() {
        return subMsg;
    }

    public String getTnCode() {
        return tnCode;
    }

    @Override
    public String getReplyId() {
        return replyId;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public String getFileContent() {
        return fileContent;
    }

    public String getQrCode() {
        return qrCode;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String getExtra() {
        final StringBuilder sb = new StringBuilder("{");
        if (null != tnCode)
            sb.append("\"tnCode\":\"").append(tnCode).append('\"');
        if (null != traceNo)
            sb.append(",\"traceNo\":\"").append(traceNo).append('\"');
        if (null != qrCode)
            sb.append(",\"qrCode\":\"").append(qrCode).append('\"');
        if (null != token)
            sb.append(",\"token\":\"").append(token).append('\"');
        sb.append('}');
        return sb.length() == 2 ? "" : sb.indexOf(",", 1) == 1 ? sb.deleteCharAt(1).toString() : sb.toString();
    }

}