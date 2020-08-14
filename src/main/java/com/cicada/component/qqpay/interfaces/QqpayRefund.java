package com.cicada.component.qqpay.interfaces;

import com.cicada.component.qqpay.enums.QqpayAction;
import com.cicada.component.qqpay.enums.QqpayParam;
import com.cicada.component.wxpay.enums.WxpayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.exception.business.IllegalTradeAmountException;
import com.cicada.utils.RequestUrlUtils;
import com.dotin.common.utils.NumberUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * qq支付退货
 * 交易时间超过一年的订单无法提交退款；
 * qq支付退款支持单笔交易分多次退款，多次退款需要提交原支付订单的商户订单号和设置不同的退款单号。
 * 申请退款总金额不能超过订单金额。 一笔退款失败后重新提交，请不要更换退款单号，请使用原商户退款单号。
 * 每个支付订单的部分退款次数不能超过50次
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.REFUND, channel = Channel.QQPAY, product = ProductType.REFUND)
public class QqpayRefund extends QqpayCancel {

    @Override
    protected String getAction() {
        return QqpayAction.REFUND.value();
    }

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = new HashMap<String, Object>(6);
        params.put(QqpayParam.TRADE_NO.value(), context.getTarget().getRecordId());//原支付单号
        params.put(QqpayParam.REFUND_NO.value(), context.getRecordId());//当前退款单号

        if (null == context.getOrder().getAmount()) {
            params.put(QqpayParam.REFUND_AMOUNT.value(), context.getTarget().getOrder().getAmount());
        } else if (NumberUtils.compare(context.getOrder().getAmount(), context.getTarget().getOrder().getAmount()) > 0) {
            throw new IllegalTradeAmountException();
        } else {
            params.put(QqpayParam.REFUND_AMOUNT.value(), context.getOrder().getAmount());
        }
        params.put(WxpayParam.NOTIFY_URL.value(), RequestUrlUtils.getNotifyUrl(context));
        params.put(WxpayParam.AMOUNT.value(), context.getTarget().getOrder().getTotalAmount());
        params.put(QqpayParam.REFUND_CURRENCY.value(), context.getOrder().getCurrency());
        params.put(QqpayParam.REFUND_DESC.value(), context.getOrder().getDesc());

        return params;
    }
}

