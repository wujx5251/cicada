package com.cicada.component.wxpay.interfaces;

import com.cicada.component.wxpay.WxpayAbstractPay;
import com.cicada.component.wxpay.bean.WxResponse;
import com.cicada.component.wxpay.enums.WxpayParam;
import com.cicada.component.wxpay.enums.WxpayTradeType;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.enums.TradingStatus;
import com.cicada.utils.PayUtils;
import com.cicada.utils.RequestUrlUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信H5支付
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.WAP_PAY, channel = Channel.WXPAY, product = ProductType.H5)
public class WxpayH5Pay extends WxpayAbstractPay {

    private final String SCENE_INFO = "{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"%s\",\"wap_name\": \"%s\"}}";

    @Override
    protected String getTradeType() {
        return WxpayTradeType.MWEB.value();
    }

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = super.getParams(context);
        params.put(WxpayParam.CLIENT_IP.value(), context.getClientIp());
        params.put(WxpayParam.SCENE_INFO.value(),
                String.format(SCENE_INFO, context.getQuitUrl(),
                        context.getOrder().getSubject()));
        return params;
    }


    @Override
    protected TradingResult render(TradingContext context, TradingResult result) {
        String action = ((WxResponse) result.getData()).getWapUrl();
        Map<String, String> params = new HashMap<String, String>(1);
        params.put(WxpayParam.RETURN_URL.value(), RequestUrlUtils.getReturnUrl(context));

        String data = PayUtils.generateHtml(action, params);
        context.getMessage().setPagePayData(data);

        action = RequestUrlUtils.getPayUrl(context);
        return result.setStatus(TradingStatus.PENDING).
                setAmount(context.getOrder().getAmount()).
                setData(action);
    }
}
