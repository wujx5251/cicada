package com.cicada.component.alipay.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.cicada.component.alipay.enums.AlipayMethod;
import com.cicada.component.alipay.enums.AlipayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;

/**
 * 支付宝-交易退货查询
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.REFUND_QUERY, channel = Channel.ALIPAY)
public class AlipayRefundQuery extends AlipayQuery {

    @Override
    protected String getMethod() {
        return AlipayMethod.REFUND_QUERY.value();
    }

    @Override
    protected JSONObject getBizContent(TradingContext context) {
        JSONObject params = new JSONObject();
        params.put(AlipayParam.TRADE_NO.value(), context.getTarget().getTarget().getRecordId());//支付交易流水号
        params.put(AlipayParam.REFUND_NO.value(), context.getTarget().getRecordId());//退货流水号
        return params;
    }

    @Override
    public TradingResult render(TradingContext context, Object params) {
        TradingResult result = super.render(context, params);
        result.setAmount(context.getTarget().getOrder().getRefundAmount());
        return result;
    }

}