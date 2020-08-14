package com.cicada.component.qqpay.interfaces;

import com.cicada.component.qqpay.QqpayAbstractPay;
import com.cicada.component.qqpay.enums.QqpayAction;
import com.cicada.component.qqpay.enums.QqpayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;

import java.util.Map;

/**
 * qq-二维码支付被扫
 * 8位字符串，开头两位为91。该字段由商户的扫码设备，从用户的手机QQ上读取，或者是店员输入
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.QR_PAY, channel = Channel.QQPAY, product = ProductType.QRCODE)
public class QqpayQrCodePay extends QqpayAbstractPay {

    @Override
    protected String getAction() {
        return QqpayAction.MICRO_PAY.value();
    }

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = super.getParams(context);
        params.put(QqpayParam.AUTH_CODE.value(), context.getExtra().getAuthCode());
        return params;
    }

}