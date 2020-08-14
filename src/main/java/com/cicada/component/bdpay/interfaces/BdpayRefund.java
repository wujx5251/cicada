package com.cicada.component.bdpay.interfaces;

import com.cicada.component.bdpay.emuns.BdpayAction;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.TradingStatus;
import com.cicada.core.exception.business.IllegalTradingStatusException;
import com.cicada.storage.PayPersistService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 百度钱包退货
 *
 * @author: WUJXIAO
 * @create: 2019-01-26 10:58
 * @version: 1.0
 **/
@Component(value = ComponetType.REFUND, channel = Channel.BDPAY)
public class BdpayRefund extends BdpayWebPay {

    @Autowired
    private PayPersistService persistService;

    @Override
    protected String getAction() {
        return BdpayAction.REFUND.value();
    }

    @Override
    public Map<String, ?> build(TradingContext context) {
        TradingStatus status = context.getTarget().getResult().getStatus();
        if (!TradingStatus.SUCCESS.equals(status)) {
            throw new IllegalTradingStatusException(status.name());
        }
        return super.build(context);
    }

    @Override
    public TradingResult render(TradingContext context, Object params) {
        TradingResult result = handle(context, params);
        if (null == result.getStatus()) {
            return render(context, result);
        }
        return result;
    }

    @Override
    public TradingResult render(TradingContext context, TradingResult result) {
        result.setAmount(context.getOrder().getAmount());
        context.getTarget().getOrder().setRefundAmount(context.getTarget().getOrder().getRefundAmount() + result.getAmount());
        persistService.updateTrade(context.getTarget());//更新原记录退款金额
        return result;
    }
}