package com.cicada.component.wxpay.bean;

import com.cicada.core.bean.Response;
import com.dotin.common.utils.DateUtils;

import java.util.Date;
import java.util.Map;

/**
 * 微信返回结果
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public class WxResponse implements Response {

    public final static String SUCCESS = "SUCCESS";
    public final static String[] PENDING = {"USERPAYING", "SYSTEMERROR", "BANKERROR"};
    public final static String CODE = "return_code";

    private String code;
    private String msg;
    private String errCode;
    private String subCode;
    private String errMsg;
    private String status;
    private String subMsg;

    private String nonceStr;
    private String qrCode;
    private String wapUrl;
    private String prepayId;
    private String sandboxSignkey;
    private String fundChannel;//资金渠道
    private String buyerId;//付款人id
    private String replyId;//支付宝流水号
    private String tradeStatus;//交易状态
    private Date payTime;
    private Date refundTime;


    private WxResponse() {
    }

    public static WxResponse from(Map<String, String> params) {
        WxResponse rsp = new WxResponse();
        rsp.code = params.get("return_code");
        rsp.msg = params.get("return_msg");
        rsp.errCode = params.get("err_code");
        rsp.subCode = params.get("result_code");
        rsp.errMsg = params.get("err_code_des");
        rsp.status = params.get("trade_state");
        rsp.subMsg = params.get("trade_state_desc");
        rsp.sandboxSignkey = params.get("sandbox_signkey");
        rsp.prepayId = params.get("prepay_id");
        rsp.qrCode = params.get("code_url");
        rsp.nonceStr = params.get("nonce_str");
        rsp.replyId = params.get("transaction_id");
        rsp.buyerId = params.get("openid");
        rsp.tradeStatus = params.get("result_code");
        rsp.fundChannel = params.get("bank_type");
        rsp.wapUrl = params.get("mweb_url");
        rsp.refundTime = DateUtils.parseDate(params.get("refund_success_time_0"), DateUtils.DATE_TIME_FORMAT);
        rsp.payTime = DateUtils.parseDate(params.get("time_end"), DateUtils.DATE_TIME_SHORT_FORMAT);
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

    public String getSubCode() {
        return subCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public String getStatus() {
        return status;
    }

    public String getSubMsg() {
        return subMsg;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public String getQrCode() {
        return qrCode;
    }

    public String getWapUrl() {
        return wapUrl;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public String getSandboxSignkey() {
        return sandboxSignkey;
    }

    public String getFundChannel() {
        return fundChannel;
    }

    public String getBuyerId() {
        return buyerId;
    }

    @Override
    public String getReplyId() {
        return replyId;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public Date getPayTime() {
        return payTime;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    @Override
    public String getExtra() {
        final StringBuilder sb = new StringBuilder("{");
        if (null != prepayId)
            sb.append("\"prepayId\":\"").append(prepayId).append('\"');
        if (null != qrCode)
            sb.append(",\"qrCode\":\"").append(qrCode).append('\"');
        sb.append('}');
        return sb.length() == 2 ? "" : sb.indexOf(",", 1) == 1 ? sb.deleteCharAt(1).toString() : sb.toString();
    }
}