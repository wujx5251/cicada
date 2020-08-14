package com.cicada.component.kqpay.interfaces;

import com.cicada.component.kqpay.KqpayAbstractNotify;
import com.cicada.core.Pay;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.enums.TradingStatus;
import com.cicada.core.exception.business.IllegalTradingStatusException;
import com.cicada.core.exception.business.NotSupportException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 快钱支付撤销整合
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.CANCEL, channel = Channel.KQPAY, product = ProductType.CANCEL)
public class KqpayIntegrateCancel extends KqpayAbstractNotify {

    @Autowired
    private Pay kqpayCancel;
    @Autowired
    private Pay kqpayAuthCancel;
    @Autowired
    private Pay kqpayAuthCompleteCancel;

    @Override
    public TradingResult doPay(TradingContext context) {
        TradingStatus status = context.getTarget().getResult().getStatus();
        if (!TradingStatus.SUCCESS.equals(status)) {
            throw new IllegalTradingStatusException(status.name());
        }
        switch (context.getTarget().getProductType()) {
            case AUTH:
                return kqpayAuthCancel.doPay(context);
            case AUTH_CONFIRM:
                return kqpayAuthCompleteCancel.doPay(context);
            case H5:
            case WEB:
            case QUICK:
                return kqpayCancel.doPay(context);
            default:
                throw new NotSupportException("the record id：" + context.getTarget().getRecordId() + " not support for cancel!");
        }
    }
}