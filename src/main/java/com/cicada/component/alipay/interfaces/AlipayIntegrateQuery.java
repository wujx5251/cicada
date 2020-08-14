package com.cicada.component.alipay.interfaces;

import com.cicada.component.alipay.AlipayAbstractNotify;
import com.cicada.core.Pay;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.exception.business.NotSupportException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 支付宝-消费状态查询整合
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.QUERY, channel = Channel.ALIPAY)
public class AlipayIntegrateQuery extends AlipayAbstractNotify {
    @Autowired
    private Pay alipayQuery;
    @Autowired
    private Pay alipayRefundQuery;

    @Override
    public TradingResult doPay(TradingContext context) {
        switch (context.getTarget().getProductType()) {
            case H5:
            case WEB:
            case APP:
            case SCAN:
            case QRCODE:
                return alipayQuery.doPay(context);
            case REFUND:
                return alipayRefundQuery.doPay(context);
            default:
                throw new NotSupportException("the record id：" + context.getTarget().getRecordId() + " not support for cancel!");
        }
    }
}