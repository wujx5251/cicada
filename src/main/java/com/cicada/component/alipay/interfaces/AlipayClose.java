package com.cicada.component.alipay.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.cicada.component.alipay.AlipayAbstractReqPay;
import com.cicada.component.alipay.enums.AlipayMethod;
import com.cicada.component.alipay.enums.AlipayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;

/**
 * 支付宝-交易关闭
 * 用于交易创建后，用户在一定时间内未进行支付，
 * 可调用该接口直接将未付款的交易进行关闭。
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.CLOSE, channel = Channel.ALIPAY, product = ProductType.CANCEL)
public class AlipayClose extends AlipayAbstractReqPay {

    @Override
    protected String getMethod() {
        return AlipayMethod.CLOSE.value();
    }

    @Override
    protected JSONObject getBizContent(TradingContext context) {
        JSONObject params = new JSONObject();
        params.put(AlipayParam.TRADE_NO.value(), context.getTarget().getRecordId());//支付交易流水号
        return params;
    }

}