package com.cicada.component.alipay.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.cicada.component.alipay.bean.AlipayConfig;
import com.cicada.component.alipay.enums.AlipayMethod;
import com.cicada.component.alipay.enums.AlipayParam;
import com.cicada.component.alipay.enums.AlipayProduct;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.enums.TradingStatus;
import com.cicada.utils.PayUtils;
import com.cicada.utils.RequestUrlUtils;

import java.util.Map;

/**
 * 支付宝支付-Web 支付
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.WEB_PAY, channel = Channel.ALIPAY, product = ProductType.WEB)
public class AlipayWebPay extends AlipayAppPay {

    @Override
    protected String getMethod() {
        return AlipayMethod.WEB_PAY.value();
    }

    @Override
    protected JSONObject getBizContent(TradingContext context) {
        JSONObject params = super.getBizContent(context);
        params.put(AlipayParam.PRODUCT.value(), AlipayProduct.WEB.value());
        params.put(AlipayParam.QUIT_URL.value(), context.getQuitUrl());
        return params;
    }

    @Override
    public TradingResult render(TradingContext context, Object params) {
        AlipayConfig config = context.getConfig();
        String data = PayUtils.generateHtml(config.getGatewayUrl()+"?charset=utf-8", (Map<String, String>) params);
        context.getMessage().setPagePayData(data);
        String url = RequestUrlUtils.getPayUrl(context);

        return new TradingResult(TradingStatus.PENDING).
                setRecordId(context.getRecordId()).
                setAmount(context.getOrder().getAmount()).
                setData(url);
    }
}