package com.cicada.component.jdpay.interfaces;

import com.cicada.component.jdpay.bean.JdResponse;
import com.cicada.component.jdpay.bean.JdpayConfig;
import com.cicada.component.jdpay.emuns.JdpayAction;
import com.cicada.component.jdpay.emuns.JdpayParam;
import com.cicada.component.jdpay.emuns.JdpayTradeType;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.enums.TradingStatus;
import com.dotin.common.utils.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 京东交易查询
 *
 * @author: WUJXIAO
 * @create: 2019-01-26 10:58
 * @version: 1.0
 **/
@Component(value = ComponetType.QUERY, channel = Channel.JDPAY)
public class JdpayQuery extends JdpayAppPay {

    @Override
    protected String getAction() {
        return JdpayAction.QUERY.value();
    }

    @Override
    public Map<String, ?> build(TradingContext context) {
        JdpayConfig config = context.getConfig();
        Map<String, Object> params = new LinkedHashMap<String, Object>(6);
        params.put(JdpayParam.VERSION.value(), config.getVersion());
        params.put(JdpayParam.MCH_ID.value(), config.getMchId());
        params.put(JdpayParam.TRADE_NO.value(), context.getTarget().getRecordId());
        if (null != context.getTarget().getTarget())
            params.put(JdpayParam.ORI_TRADE_NO.value(), context.getTarget().getTarget().getRecordId());

        params.put(JdpayParam.TRADE_TYPE.value(), JdpayTradeType.CONSUME.value());
        if (ProductType.REFUND.equals(context.getTarget().getProductType())) {
            params.put(JdpayParam.TRADE_TYPE.value(), JdpayTradeType.REFUND.value());
        }

        String sign = sign(config, params);
        params.put(JdpayParam.SIGN.value(), sign);
        return encrypt(config, params);
    }

    @Override
    public TradingResult render(TradingContext context, Object params) {
        TradingResult result = super.render(context, params);
        result.setRecordId(context.getTarget().getRecordId());
        result.setAmount(context.getTarget().getOrder().getAmount());

        JdResponse rsp = (JdResponse) result.getData();

        if (TradingStatus.FAIL.equals(result.getStatus())
                && null == rsp.getStatus()) {
            result.setErrMsg(StringUtils.concat("[", rsp.getCode(), "]", rsp.getMsg()));
            result.setStatus(context.getTarget().getResult().getStatus());
        }
        return result;
    }

    @Override
    public TradingResult render(TradingContext context, TradingResult result) {
        return result;
    }
}