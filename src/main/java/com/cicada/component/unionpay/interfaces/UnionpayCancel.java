package com.cicada.component.unionpay.interfaces;

import com.cicada.component.unionpay.UnionpayAbstractReqPay;
import com.cicada.component.unionpay.enums.*;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.exception.business.IllegalTradeAmountException;
import com.cicada.storage.PayPersistService;
import com.dotin.common.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 银联支付撤销
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.QUICK_CANCEL, channel = Channel.UNIONPAY, product = ProductType.CANCEL)
public class UnionpayCancel extends UnionpayAbstractReqPay {

    @Autowired
    private PayPersistService persistService;

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = super.getParams(context);
        params.put(UnionpayParam.BIZ_TYPE.value(), UnionpayBizType.TOKEN_PAY);
        params.put(UnionpayParam.TXN_TYPE.value(), UnionpayTxnType.CANCEL);
        params.put(UnionpayParam.TXN_SUB_TYPE.value(), UnionpaySubTxnType.DEFAULT);

        params.put(UnionpayParam.ORIG_QRY_ID.value(), context.getTarget().getReplyId());//原单银联流水号

        if (null == context.getOrder().getAmount()) {
            params.put(UnionpayParam.AMOUNT.value(), context.getTarget().getOrder().getAmount());
        } else if (NumberUtils.compare(context.getOrder().getAmount(), context.getTarget().getOrder().getAmount()) > 0) {
            throw new IllegalTradeAmountException();
        } else {
            params.put(UnionpayParam.AMOUNT.value(), context.getOrder().getAmount());
        }
        params.put(UnionpayParam.CURRENCY.value(), UnionpayCurrency.from(context.getTarget().getOrder().getCurrency()));
        return params;
    }

    @Override
    protected TradingResult render(TradingContext context, TradingResult result) {
        super.render(context, result);
        context.getTarget().getOrder().setRefundAmount(context.getTarget().getOrder().getRefundAmount() + result.getAmount());
        persistService.updateTrade(context.getTarget());//更新原记录退款金额
        return result;
    }
}