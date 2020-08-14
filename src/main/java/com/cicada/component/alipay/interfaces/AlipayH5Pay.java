package com.cicada.component.alipay.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.cicada.component.alipay.enums.AlipayMethod;
import com.cicada.component.alipay.enums.AlipayParam;
import com.cicada.component.alipay.enums.AlipayProduct;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;

/**
 * 支付宝支付-Wap 支付
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.WAP_PAY, channel = Channel.ALIPAY, product = ProductType.H5)
public class AlipayH5Pay extends AlipayWebPay {

    @Override
    protected String getMethod() {
        return AlipayMethod.WAP_PAY.value();
    }

    @Override
    protected JSONObject getBizContent(TradingContext context) {
        JSONObject params = super.getBizContent(context);
        params.put(AlipayParam.PRODUCT.value(), AlipayProduct.WAP.value());
        return params;
    }
}