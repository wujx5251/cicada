package com.cicada.component.wxpay.interfaces;

import com.cicada.component.wxpay.WxpayAbstractPay;
import com.cicada.component.wxpay.bean.WxResponse;
import com.cicada.component.wxpay.bean.WxpayConfig;
import com.cicada.component.wxpay.enums.WxpayParam;
import com.cicada.component.wxpay.enums.WxpayTradeType;
import com.cicada.component.wxpay.support.WxpaySecurity;
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
 * 微信 App支付
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.APP_PAY, channel = Channel.WXPAY, product = ProductType.APP)
public class WxpayAppPay extends WxpayAbstractPay {

    @Override
    protected String getTradeType() {
        return WxpayTradeType.APP.value();
    }

    @Override
    protected TradingResult render(TradingContext context, TradingResult result) {
        WxpayConfig config = context.getConfig();
        Map<String, Object> params = new HashMap<String, Object>(7);
        params.put(WxpayParam.APP_ID.value(), config.getAppId());
        params.put(WxpayParam.PARTNERID.value(), config.getMchId());
        params.put(WxpayParam.PREPAYID.value(), ((WxResponse) result.getData()).getPrepayId());
        params.put(WxpayParam.NONCESTR.value(), ((WxResponse) result.getData()).getNonceStr());
        params.put(WxpayParam.PACKAGE.value(), "Sign=WXPay");
        params.put(WxpayParam.TIMESTAMP.value(), context.getCreateTime() / 1000);
        params.put(WxpayParam.SIGN.value(), WxpaySecurity.instance.sign(config, params));

        return result.setStatus(TradingStatus.PENDING).
                setAmount(context.getOrder().getAmount()).
                setData(params);
    }

}