package com.cicada.component.wxpay.interfaces;

import com.cicada.component.wxpay.WxpayAbstractNotify;
import com.cicada.core.Pay;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.exception.business.NotSupportException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 微信-消费状态查询整合
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.QUERY, channel = Channel.WXPAY)
public class WxpayIntegrateQuery extends WxpayAbstractNotify {
    @Autowired
    private Pay wxpayQuery;
    @Autowired
    private Pay wxpayRefundQuery;

    @Override
    public TradingResult doPay(TradingContext context) {
        switch (context.getTarget().getProductType()) {
            case H5:
            case JSAPI:
            case APP:
            case SCAN:
            case QRCODE:
                return wxpayQuery.doPay(context);
            case REFUND:
                return wxpayRefundQuery.doPay(context);
            default:
                throw new NotSupportException("the record id：" + context.getTarget().getRecordId() + " not support for cancel!");
        }
    }
}