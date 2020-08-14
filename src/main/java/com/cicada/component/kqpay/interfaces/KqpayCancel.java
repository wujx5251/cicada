package com.cicada.component.kqpay.interfaces;

import com.cicada.component.kqpay.bean.KqPartObject;
import com.cicada.component.kqpay.enums.KqTxnType;
import com.cicada.component.kqpay.enums.KqpayAction;
import com.cicada.component.kqpay.enums.KqpayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.exception.business.IllegalTradeAmountException;
import com.cicada.storage.PayPersistService;
import com.cicada.utils.PayUtils;
import com.dotin.common.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 快钱支付撤销
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.QUICK_CANCEL, channel = Channel.KQPAY, product = ProductType.CANCEL)
public class KqpayCancel extends KqpayQuickPay {

    @Autowired
    private PayPersistService persistService;

    @Override
    protected String getAction() {
        return KqpayAction.CANCEL.value();
    }

    @Override
    protected KqPartObject getParam(TradingContext context) {
        KqPartObject param = super.getParam(context);
        param.put(KqpayParam.TXN_TYPE.value(), KqTxnType.VTX);
        param.put(KqpayParam.REF_NUMBER.value(), context.getTarget().getReplyId());
        param.put(KqpayParam.ORIG_TXN_TYPE.value(), KqTxnType.PUR);
        if (null == context.getOrder().getAmount()) {
            param.put(KqpayParam.AMOUNT.value(), PayUtils.getCentMoney(context.getTarget().getOrder().getAmount()));
        } else if (NumberUtils.compare(context.getOrder().getAmount(), context.getTarget().getOrder().getAmount()) > 0) {
            throw new IllegalTradeAmountException();
        }
        param.remove(KqpayParam.EXT_MAP.value());
        param.remove(KqpayParam.SP_FLAG.value());
        return param;
    }

    @Override
    protected TradingResult render(TradingContext context, TradingResult result) {
        super.render(context, result);
        context.getTarget().getOrder().setRefundAmount(context.getTarget().getOrder().getRefundAmount() + result.getAmount());
        persistService.updateTrade(context.getTarget());//更新原记录退款金额
        return result;
    }

    @Override
    protected String getShortCardNo(TradingContext context) {
        return null;
    }

    @Override
    protected String getToken(TradingContext context) {
        return null;
    }

    @Override
    protected void smsInfo(KqPartObject params, TradingContext target) {

    }
}