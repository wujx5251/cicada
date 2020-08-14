package com.cicada.component.jdpay.interfaces;

import com.cicada.component.jdpay.bean.JdpayConfig;
import com.cicada.component.jdpay.emuns.JdpayAction;
import com.cicada.component.jdpay.emuns.JdpayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.TradingStatus;
import com.cicada.core.exception.business.IllegalTradeAmountException;
import com.cicada.core.exception.business.IllegalTradingStatusException;
import com.cicada.utils.RequestUrlUtils;
import com.dotin.common.utils.NumberUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 京东退货
 *
 * @author: WUJXIAO
 * @create: 2019-01-26 10:58
 * @version: 1.0
 **/
@Component(value = ComponetType.REFUND, channel = Channel.JDPAY)
public class JdpayRefund extends JdpayCancel {

    @Override
    protected String getAction() {
        return JdpayAction.REFUND.value();
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
        if (null == context.getOrder().getAmount()) {
            params.put(JdpayParam.AMOUNT.value(), context.getTarget().getOrder().getAmount());
        } else if (NumberUtils.compare(context.getOrder().getAmount(), context.getTarget().getOrder().getAmount()) > 0) {
            throw new IllegalTradeAmountException();
        } else {
            params.put(JdpayParam.AMOUNT.value(), context.getOrder().getAmount());
        }
        params.put(JdpayParam.CURRENCY.value(), context.getOrder().getCurrency());
        params.put(JdpayParam.NOTIFY_URL.value(), RequestUrlUtils.getNotifyUrl(context));

        String sign = sign(config, params);
        params.put(JdpayParam.SIGN.value(), sign);
        return encrypt(config, params);
    }

}