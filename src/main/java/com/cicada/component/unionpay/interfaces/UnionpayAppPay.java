package com.cicada.component.unionpay.interfaces;

import com.cicada.component.unionpay.UnionpayAbstractReqPay;
import com.cicada.component.unionpay.bean.UnResponse;
import com.cicada.component.unionpay.bean.UnionpayConfig;
import com.cicada.component.unionpay.enums.*;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.enums.TradingStatus;
import com.dotin.common.utils.DateUtils;

import java.util.Date;
import java.util.Map;

/**
 * 银联 App支付，兼容applypay
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.APP_PAY, channel = Channel.UNIONPAY, product = ProductType.APP)
public class UnionpayAppPay extends UnionpayAbstractReqPay {

    @Override
    protected String getAction() {
        return UnionpayAction.APP_REQ.value();
    }

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = super.getParams(context);
        UnionpayConfig config = context.getConfig();
        params.put(UnionpayParam.AMOUNT.value(), context.getOrder().getAmount());
        params.put(UnionpayParam.CURRENCY.value(), UnionpayCurrency.from(context.getOrder().getCurrency()));
        params.put(UnionpayParam.TIMEOUT.value(), config.getTimeout());
        params.put(UnionpayParam.PAY_TIMEOUT.value(), DateUtils.format(DateUtils.addMinutes(new Date(), config.getTimeout()),
                DateUtils.DATE_TIME_SHORT_FORMAT));
        params.put(UnionpayParam.SUBJECT.value(), context.getOrder().getSubject());

        params.put(UnionpayParam.BIZ_TYPE.value(), UnionpayBizType.B2C_GATEWAY_PAY);
        params.put(UnionpayParam.TXN_TYPE.value(), UnionpayTxnType.CONSUME);
        params.put(UnionpayParam.TXN_SUB_TYPE.value(), UnionpaySubTxnType.CONSUME);
        return params;
    }

    @Override
    public TradingResult render(TradingContext context, TradingResult result) {

        return result.setStatus(TradingStatus.PENDING).
                setAmount(context.getOrder().getAmount()).
                setData(((UnResponse) result.getData()).getTnCode());
    }
}