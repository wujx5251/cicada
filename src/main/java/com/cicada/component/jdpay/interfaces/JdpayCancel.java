package com.cicada.component.jdpay.interfaces;

import com.cicada.component.jdpay.bean.JdResponse;
import com.cicada.component.jdpay.bean.JdpayConfig;
import com.cicada.component.jdpay.emuns.JdpayAction;
import com.cicada.component.jdpay.emuns.JdpayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.TradingStatus;
import com.cicada.core.exception.business.IllegalTradingStatusException;
import com.cicada.storage.PayPersistService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 京东交易撤销
 *
 * @author: WUJXIAO
 * @create: 2019-01-26 10:58
 * @version: 1.0
 **/
@Component(value = ComponetType.CANCEL, channel = Channel.JDPAY)
public class JdpayCancel extends JdpayAppPay {

    @Autowired
    private PayPersistService persistService;

    @Override
    protected String getAction() {
        return JdpayAction.CANCEL.value();
    }

    @Override
    public Map<String, ?> build(TradingContext context) {
        TradingStatus status = context.getTarget().getResult().getStatus();
        if (!TradingStatus.SUCCESS.equals(status)) {
            throw new IllegalTradingStatusException(status.name());
        }

        JdpayConfig config = context.getConfig();
        Map<String, Object> params = new LinkedHashMap<String, Object>(8);
        params.put(JdpayParam.VERSION.value(), config.getVersion());
        params.put(JdpayParam.MCH_ID.value(), config.getMchId());
        params.put(JdpayParam.TRADE_NO.value(), context.getRecordId());
        params.put(JdpayParam.ORI_TRADE_NO.value(), context.getTarget().getRecordId());
        params.put(JdpayParam.AMOUNT.value(), context.getOrder().getAmount());
        params.put(JdpayParam.CURRENCY.value(), context.getOrder().getCurrency());

        String sign = sign(config, params);
        params.put(JdpayParam.SIGN.value(), sign);
        return encrypt(config, params);
    }

    @Override
    public TradingResult render(TradingContext context, Object params) {
        TradingResult result = super.render(context, params);

        JdResponse rsp = (JdResponse) result.getData();

        if ("1".equals(rsp.getStatus())) {
            result.setStatus(TradingStatus.SUCCESS);
            render(context, result);
        } else if ("0".equals(rsp.getStatus())) {
            result.setStatus(TradingStatus.PENDING);
        } else {
            result.setStatus(TradingStatus.FAIL);
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