package com.cicada.component.bdpay.bean;

import com.cicada.core.bean.Response;
import com.dotin.common.utils.DateUtils;

import java.util.Date;
import java.util.Map;

/**
 * 百度钱包返回结果
 *
 * @author: WUJXIAO
 * @create: 2019-1-13 08:58
 * @version: 1.0
 **/
public class BdResponse implements Response {

    public final static String SUCCESS = "0";
    public final static String REFUND = "3";
    public final static String PENDING = "2";

    private String payStatus;
    private String queryStatus;
    private String replyId;//百度流水号
    private Date payTime;


    private BdResponse() {
    }

    public static BdResponse from(Map<String, String> params) {
        BdResponse rsp = new BdResponse();
        rsp.payStatus = params.get("pay_result");
        rsp.queryStatus = params.get("query_status");
        rsp.replyId = params.get("bfb_order_no");
        rsp.payTime = DateUtils.parseDate(params.get("pay_time"), DateUtils.DATE_TIME_SHORT_FORMAT);
        return rsp;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public String getQueryStatus() {
        return queryStatus;
    }

    @Override
    public String getReplyId() {
        return replyId;
    }

    public Date getPayTime() {
        return payTime;
    }

    @Override
    public String getExtra() {
        return null;
    }
}