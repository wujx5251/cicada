package com.cicada.component.alipay.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.cicada.component.alipay.AlipayAbstractReqPay;
import com.cicada.component.alipay.bean.AliResponse;
import com.cicada.component.alipay.enums.AlipayMethod;
import com.cicada.component.alipay.enums.AlipayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.requester.AbstractRequester;
import com.cicada.core.requester.RequestExecutor;
import com.dotin.common.net.http.Response;
import com.dotin.common.utils.DateUtils;

import java.util.Map;

/**
 * 支付宝-对账单查询
 * 账单下载地址链接，获取连接后30秒后未下载，链接地址失效。
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.BILL_DOWN, channel = Channel.ALIPAY)
public class AlipayBillQuery extends AlipayAbstractReqPay {

    @Override
    protected String getMethod() {
        return AlipayMethod.BILL.value();
    }

    @Override
    protected JSONObject getBizContent(TradingContext context) {
        JSONObject params = new JSONObject();
        params.put(AlipayParam.BILL_TYPE.value(), "trade");//账单类型,trade指商户基于支付宝交易收单的业务账单
        params.put(AlipayParam.BILL_DATE.value(), DateUtils.format(context.getExtra().getBillDate(), DateUtils.DATE_FORMAT));//账单时间
        return params;
    }

    @Override
    protected TradingResult render(TradingContext context, final TradingResult result) {
        RequestExecutor executor = new AbstractRequester() {
            @Override
            public Object parse(TradingContext context, Response rsp) {
                return null;
            }

            @Override
            public Response execute(TradingContext context, Map params) {
                return doRequest(((AliResponse) result.getData()).getBillUrl());
            }
        };

        Response rs = executor.execute(null, null);
        return null;
    }
}