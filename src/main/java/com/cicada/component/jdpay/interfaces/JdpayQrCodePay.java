package com.cicada.component.jdpay.interfaces;

import com.cicada.component.jdpay.emuns.JdpayAction;
import com.cicada.component.jdpay.emuns.JdpayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.enums.TradingStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 京东二维码 支付
 * 18开头18位付款码、62开头19位付款码
 *
 * @author: WUJXIAO
 * @create: 2019-01-26 10:58
 * @version: 1.0
 **/
@Component(value = ComponetType.QR_PAY, channel = Channel.JDPAY, product = ProductType.QRCODE)
public class JdpayQrCodePay extends JdpayAppPay {

    @Override
    protected String getAction() {
        return JdpayAction.MICRO_PAY.value();
    }

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put(JdpayParam.AUTH_CODE.value(), context.getExtra().getAuthCode());
        return params;
    }

    @Override
    public TradingResult render(TradingContext context, TradingResult result) {
        if (null == result.getStatus())
            result.setStatus(TradingStatus.SUCCESS);

        return result.setRecordId(context.getRecordId()).
                setAmount(context.getOrder().getAmount());
    }
}