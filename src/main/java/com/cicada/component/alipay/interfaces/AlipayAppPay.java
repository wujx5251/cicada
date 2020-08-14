package com.cicada.component.alipay.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.cicada.component.alipay.AlipayAbstractPay;
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

import java.util.Map;

/**
 * 支付宝app支付
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.APP_PAY, channel = Channel.ALIPAY, product = ProductType.APP)
public class AlipayAppPay extends AlipayAbstractPay {

    @Override
    protected String getMethod() {
        return AlipayMethod.APP_PAY.value();
    }

    @Override
    protected JSONObject getBizContent(TradingContext context) {
        JSONObject params = super.getBizContent(context);
        params.put(AlipayParam.PRODUCT.value(), AlipayProduct.APP.value());
        return params;
    }

    @Override
    public TradingResult render(TradingContext context, Object parameters) {
        String data = PayUtils.getQueryStr((Map<String, String>) parameters, true);
        return new TradingResult(TradingStatus.PENDING).
                setRecordId(context.getRecordId()).
                setAmount(context.getOrder().getAmount()).
                setData(data);
    }

}