package com.cicada.component.alipay.bean;

import com.cicada.core.bean.Response;
import com.dotin.common.utils.DateUtils;
import com.dotin.common.utils.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * 支付宝返回结果
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public class AliResponse implements Response {

    public final static String SUCCESS = "10000";
    public final static String[] PENDING = {"10003", "20000"};

    private String code;
    private String msg;
    private String subCode;
    private String subMsg;
    private String status;//交易状态

    private String qrCode;//二维码信息
    private String fundChannel;//资金渠道
    private String buyerId;//付款人id
    private String replyId;//支付宝流水号
    private Date payTime;//付款时间
    private Date refundTime;//退款时间
    private String billUrl;//账单路径

    private AliResponse() {
    }

    public static AliResponse from(Map<String, String> params) {
        AliResponse rsp = new AliResponse();
        rsp.code = params.get("code");
        rsp.msg = params.get("msg");
        rsp.subCode = params.get("sub_code");
        rsp.subMsg = params.get("sub_msg");
        rsp.qrCode = params.get("qr_code");
        rsp.fundChannel = StringUtils.parse(params.get("fund_bill_list"));
        rsp.buyerId = params.get("buyer_id");
        if (null == rsp.buyerId) {
            rsp.buyerId = params.get("buyer_user_id");
        }
        rsp.replyId = params.get("trade_no");
        rsp.status = params.get("trade_status");
        rsp.payTime = DateUtils.parseDate(params.get("gmt_payment"), DateUtils.DATE_TIME_FORMAT);
        if (null == rsp.payTime) {
            rsp.payTime = DateUtils.parseDate(params.get("send_pay_date"), DateUtils.DATE_TIME_FORMAT);
        }

        rsp.refundTime = DateUtils.parseDate(params.get("gmt_refund"), DateUtils.DATE_TIME_FORMAT);
        if (null == rsp.refundTime) {
            rsp.refundTime = DateUtils.parseDate(params.get("gmt_refund_pay"), DateUtils.DATE_TIME_FORMAT);
        }
        rsp.billUrl = params.get("bill_download_url");
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

    public String getQrCode() {
        return qrCode;
    }

    public String getFundChannel() {
        return fundChannel;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public String getStatus() {
        return status;
    }

    public Date getPayTime() {
        return payTime;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public String getBillUrl() {
        return billUrl;
    }

    @Override
    public String getReplyId() {
        return replyId;
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