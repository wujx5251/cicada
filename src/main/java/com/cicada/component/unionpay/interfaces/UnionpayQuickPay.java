package com.cicada.component.unionpay.interfaces;

import com.cicada.component.unionpay.bean.UnionpayConfig;
import com.cicada.component.unionpay.enums.UnionpayCurrency;
import com.cicada.component.unionpay.enums.UnionpayParam;
import com.cicada.component.unionpay.enums.UnionpaySubTxnType;
import com.cicada.component.unionpay.enums.UnionpayTxnType;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.dotin.common.utils.DateUtils;

import java.util.Date;
import java.util.Map;

/**
 * 银联快捷 支付
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.QUICK_PAY, channel = Channel.UNIONPAY, product = ProductType.QUICK)
public class UnionpayQuickPay extends UnionpaySms {

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = super.getParams(context);
        UnionpayConfig config = context.getConfig();
        params.put(UnionpayParam.TXN_TYPE.value(), UnionpayTxnType.CONSUME);
        params.put(UnionpayParam.TXN_SUB_TYPE.value(), UnionpaySubTxnType.CONSUME);

        smsInfo(params, context.getTarget());

        params.put(UnionpayParam.TIMEOUT.value(), config.getTimeout());
        params.put(UnionpayParam.PAY_TIMEOUT.value(), DateUtils.format(DateUtils.addMinutes(new Date(), config.getTimeout()),
                DateUtils.DATE_TIME_SHORT_FORMAT));
        params.put(UnionpayParam.SUBJECT.value(), context.getOrder().getSubject());
        return params;
    }

    protected void smsInfo(Map<String, Object> params, TradingContext target) {
        if (null != target) {//短信二次确认支付
            params.put(UnionpayParam.TRADE_NO.value(), target.getRecordId());
            params.put(UnionpayParam.CURRENCY.value(), UnionpayCurrency.from(target.getOrder().getCurrency()));
            params.put(UnionpayParam.TIMESTAMP.value(),
                    DateUtils.format(target.getCreateTime(), DateUtils.DATE_TIME_SHORT_FORMAT));//原单时间
        }
    }

    @Override
    protected String getToken(TradingContext context) {
        if (null != context.getTarget()) {
            return super.getToken(context.getTarget());
        } else {
            return super.getToken(context);
        }
    }

}