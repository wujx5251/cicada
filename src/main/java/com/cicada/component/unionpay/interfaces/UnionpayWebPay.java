package com.cicada.component.unionpay.interfaces;

import com.cicada.component.unionpay.bean.UnionpayConfig;
import com.cicada.component.unionpay.enums.UnionpayAction;
import com.cicada.component.unionpay.enums.UnionpayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.enums.TradingStatus;
import com.cicada.core.requester.RequestExecutor;
import com.cicada.utils.PayUtils;
import com.cicada.utils.RequestUrlUtils;

import java.util.Map;

/**
 * 银联Web 支付
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.WEB_PAY, channel = Channel.UNIONPAY, product = ProductType.WEB)
public class UnionpayWebPay extends UnionpayAppPay {

    @Override
    protected String getAction() {
        return UnionpayAction.FRONT_REQ.value();
    }

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = super.getParams(context);
        params.put(UnionpayParam.RETURN_URL.value(), RequestUrlUtils.getReturnUrl(context));
        params.put(UnionpayParam.QUIT_URL.value(), context.getQuitUrl());

        if (null != context.getCard())
            params.put(UnionpayParam.INS_CODE.value(), context.getCard().getOrgCode());
        return params;
    }

    @Override
    public TradingResult render(TradingContext context, Object params) {
        UnionpayConfig config = context.getConfig();
        String data = PayUtils.generateHtml(config.getGatewayUrl() + getAction(), (Map<String, String>) params);
        context.getMessage().setPagePayData(data);
        String url = RequestUrlUtils.getPayUrl(context);

        return new TradingResult(TradingStatus.PENDING).
                setRecordId(context.getRecordId()).
                setAmount(context.getOrder().getAmount()).
                setData(url);
    }

    @Override
    protected RequestExecutor getRequestExecutor(TradingContext context) {
        return null;
    }
}