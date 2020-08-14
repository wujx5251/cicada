package com.cicada.component.alipay.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.cicada.component.alipay.AlipayAbstractReqPay;
import com.cicada.component.alipay.enums.AlipayMethod;
import com.cicada.component.alipay.enums.AlipayParam;
import com.cicada.component.alipay.enums.AlipayProduct;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;

/**
 * 支付宝-二维码支付被扫
 * 支付授权码，25~30开头的长度为16~24位
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.QR_PAY, channel = Channel.ALIPAY, product = ProductType.QRCODE)
public class AlipayQrCodePay extends AlipayAbstractReqPay {

    @Override
    protected String getMethod() {
        return AlipayMethod.QR_PAY.value();
    }

    @Override
    protected JSONObject getBizContent(TradingContext context) {
        JSONObject params = super.getBizContent(context);
        params.put(AlipayParam.PRODUCT.value(), AlipayProduct.F2F.value());
        params.put(AlipayParam.SCENE.value(), "bar_code");
        params.put(AlipayParam.AUTH_CODE.value(), context.getExtra().getAuthCode());//支付授权码
        params.put(AlipayParam.CURRENCY.value(), context.getOrder().getCurrency());//标价币种,total_amount 对应的币种单位
        params.put(AlipayParam.SETTLE_CURRENCY.value(), context.getOrder().getCurrency());//商户指定的结算币种
        return params;
    }


}