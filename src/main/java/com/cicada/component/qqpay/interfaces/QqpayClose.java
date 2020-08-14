package com.cicada.component.qqpay.interfaces;

import com.cicada.component.qqpay.QqpayAbstractPay;
import com.cicada.component.qqpay.enums.QqpayAction;
import com.cicada.component.qqpay.enums.QqpayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;

import java.util.HashMap;
import java.util.Map;

/**
 * qq支付交易关闭
 * 系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.CLOSE, channel = Channel.QQPAY, product = ProductType.CANCEL)
public class QqpayClose extends QqpayAbstractPay {

    @Override
    protected String getAction() {
        return QqpayAction.CLOSE.value();
    }

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put(QqpayParam.TRADE_NO.value(), context.getTarget().getRecordId());
        return params;
    }
}