package com.cicada.component.alipay.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.cicada.component.alipay.enums.AlipayMethod;
import com.cicada.component.alipay.enums.AlipayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.exception.business.IllegalTradeAmountException;
import com.cicada.utils.PayUtils;
import com.dotin.common.utils.NumberUtils;

/**
 * 支付宝-交易退货
 * 交易超过约定时间（签约时设置的可退款时间）的订单无法进行退款3个月
 * 支付宝退款支持单笔交易分多次退款，多次退款需要提交原支付订单的商户订单号和设置不同的退款单号。
 * 一笔退款失败后重新提交，要采用原来的退款单号。总退款金额不能超过用户实际支付金额
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.REFUND, channel = Channel.ALIPAY, product = ProductType.REFUND)
public class AlipayRefund extends AlipayCancel {

    @Override
    protected String getMethod() {
        return AlipayMethod.REFUND.value();
    }

    @Override
    protected JSONObject getBizContent(TradingContext context) {
        JSONObject params = super.getBizContent(context);
        params.put(AlipayParam.REFUND_NO.value(), context.getRecordId());//当前交易流水号
        if (null == context.getOrder().getAmount()) {
            params.put(AlipayParam.REFUND_AMOUNT.value(), PayUtils.getCentMoney(context.getTarget().getOrder().getAmount()));
        } else if (NumberUtils.compare(context.getOrder().getAmount(), context.getTarget().getOrder().getAmount()) > 0) {
            throw new IllegalTradeAmountException();
        } else {
            params.put(AlipayParam.REFUND_AMOUNT.value(), PayUtils.getCentMoney(context.getOrder().getAmount()));
        }
        params.put(AlipayParam.REFUND_CURRENCY.value(), context.getOrder().getCurrency());
        params.put(AlipayParam.REFUND_DESC.value(), context.getOrder().getDesc());
        return params;
    }

}