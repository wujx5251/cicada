package com.cicada.component.kqpay.bean;

import com.cicada.component.kqpay.enums.KqCardType;
import com.cicada.core.bean.Response;
import com.cicada.core.enums.CardType;
import com.dotin.common.utils.ObjectUtils;

import java.util.Map;

/**
 * 快钱返回结果
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public class KqResponse implements Response {

    public final static String SUCCESS = "00";
    public final static String[] PENDING = {"C0", "68"};

    private String code;
    private String msg;
    private String errCode;
    private String errMsg;

    private String openId;//令牌信息
    private Boolean valid;//快钱VPOS-CNP是否支持1：快钱支持 ,2：快钱不支持
    private CardType cardType;
    private String token;
    private String authCode;//预授权授权码
    private String bankName;//发卡行名称
    private String amount;
    private String bankCode;//银行代码
    private String replyId;//快钱流水号
    private String status;//交易状态

    private KqResponse() {
    }

    public static KqResponse from(Map<String, String> params) {
        KqResponse rsp = new KqResponse();
        rsp.code = params.get("responseCode");
        rsp.msg = params.get("responseTextMessage");
        rsp.errCode = params.get("errorCode");
        rsp.errMsg = params.get("errorMessage");
        rsp.openId = params.get("token");
        rsp.token = params.get("payToken");
        rsp.amount = params.get("amount");
        rsp.status = params.get("txnStatus");
        rsp.bankName = params.get("issuer");
        rsp.authCode = params.get("authorizationCode");
        rsp.bankCode = params.get("bankId");
        if (null != params.get("validFlag"))
            rsp.valid = ObjectUtils.isEquals("1", params.get("validFlag"));
        rsp.cardType = KqCardType.from(params.get("cardType"));
        rsp.replyId = params.get("refNumber");
        return rsp;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public String getOpenId() {
        return openId;
    }

    public Boolean getValid() {
        return valid;
    }

    public CardType getCardType() {
        return cardType;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public String getAuthCode() {
        return authCode;
    }

    public String getToken() {
        return token;
    }

    public String getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String getReplyId() {
        return replyId;
    }


    @Override
    public String getExtra() {
        final StringBuilder sb = new StringBuilder("{");
        if (null != authCode)
            sb.append("\"authCode\":\"").append(authCode).append('\"');
        if (null != openId)
            sb.append(",\"openId\":\"").append(openId).append('\"');
        if (null != token)
            sb.append(",\"token\":\"").append(token).append('\"');
        sb.append('}');
        return sb.length() == 2 ? "" : sb.indexOf(",", 1) == 1 ? sb.deleteCharAt(1).toString() : sb.toString();
    }
}