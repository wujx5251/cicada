package com.cicada.component.kqpay.interfaces;

import com.cicada.component.kqpay.bean.KqPartObject;
import com.cicada.component.kqpay.enums.KqTxnType;
import com.cicada.component.kqpay.enums.KqpayAction;
import com.cicada.component.kqpay.enums.KqpayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.enums.TradingStatus;
import com.cicada.core.exception.business.IllegalTradingStatusException;

import java.util.Map;

/**
 * 快钱支付退货
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.REFUND, channel = Channel.KQPAY, product = ProductType.REFUND)
public class KqpayRefund extends KqpayCancel {

    @Override
    protected String getAction() {
        return KqpayAction.REFUND.value();
    }

    @Override
    public Map<String, Object> build(TradingContext context) {
        TradingStatus status = context.getTarget().getResult().getStatus();
        if (!TradingStatus.SUCCESS.equals(status)) {
            throw new IllegalTradingStatusException(status.name());
        }
        return super.build(context);
    }

    @Override
    protected KqPartObject getParam(TradingContext context) {
        KqPartObject param = super.getParam(context);
        param.put(KqpayParam.ORIG_REF_NUMBER.value(), context.getTarget().getReplyId());
        param.put(KqpayParam.TXN_TYPE.value(), KqTxnType.RFD);
        param.remove(KqpayParam.REF_NUMBER.value());
        return param;
    }
}