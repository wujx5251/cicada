package com.cicada.component.unionpay.interfaces;

import com.cicada.component.unionpay.UnionpayAbstractNotify;
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
 * 银联支付撤销整合
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.CANCEL, channel = Channel.UNIONPAY, product = ProductType.CANCEL)
public class UnionpayIntegrateCancel extends UnionpayAbstractNotify {

    @Autowired
    private Pay unionpayCancel;
    @Autowired
    private Pay unionpayAuthCancel;
    @Autowired
    private Pay unionpayAuthCompleteCancel;

    @Override
    public TradingResult doPay(TradingContext context) {
        TradingStatus status = context.getTarget().getResult().getStatus();
        if (!TradingStatus.SUCCESS.equals(status)) {
            throw new IllegalTradingStatusException(status.name());
        }

        switch (context.getTarget().getProductType()) {
            case AUTH:
                return unionpayAuthCancel.doPay(context);
            case AUTH_CONFIRM:
                return unionpayAuthCompleteCancel.doPay(context);
            case H5:
            case WEB:
            case APP:
            case SCAN:
            case QRCODE:
            case QUICK:
                return unionpayCancel.doPay(context);
            default:
                throw new NotSupportException("the record id：" + context.getTarget().getRecordId() + " not support for cancel!");
        }
    }
}