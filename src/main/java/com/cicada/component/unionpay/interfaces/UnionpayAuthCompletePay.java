package com.cicada.component.unionpay.interfaces;

import com.cicada.component.unionpay.enums.UnionpayParam;
import com.cicada.component.unionpay.enums.UnionpayTxnType;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.exception.business.IllegalTradeAmountException;
import com.cicada.utils.PayUtils;
import com.dotin.common.utils.NumberUtils;

import java.util.Map;

/**
 * 银联预授权完成支付
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.AUTH_CFM_PAY, channel = Channel.UNIONPAY, product = ProductType.AUTH_CONFIRM)
public class UnionpayAuthCompletePay extends UnionpayQuickPay {

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = super.getParams(context);
        params.put(UnionpayParam.TXN_TYPE.value(), UnionpayTxnType.AUTH_COMPLETE);

        params.put(UnionpayParam.ORIG_QRY_ID.value(), context.getTarget().getReplyId());//原单银联流水号
        if (null == context.getOrder().getAmount()) {
            params.put(UnionpayParam.AMOUNT.value(), PayUtils.getCentMoney(context.getTarget().getOrder().getAmount()));
        } else if (NumberUtils.compare(context.getOrder().getAmount(), context.getTarget().getOrder().getAmount()) > 0) {
            throw new IllegalTradeAmountException();
        }
        return params;
    }

    @Override
    protected void smsInfo(Map<String, Object> params, TradingContext target) {

    }
}