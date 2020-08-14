package com.cicada.component.jdpay.interfaces;

import com.cicada.component.jdpay.JdpayAbstractPay;
import com.cicada.component.jdpay.bean.JdpayConfig;
import com.cicada.component.jdpay.emuns.JdpayParam;
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

import java.util.HashMap;
import java.util.Map;

/**
 * 京东Web 支付
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.WEB_PAY, channel = Channel.JDPAY, product = ProductType.WEB)
public class JdpayWebPay extends JdpayAbstractPay {

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put(JdpayParam.RETURN_URL.value(), RequestUrlUtils.getReturnUrl(context));
        return params;
    }


    @Override
    public TradingResult render(TradingContext context, Object params) {
        JdpayConfig config = context.getConfig();
        String data = PayUtils.generateHtml(config.getGatewayUrl(), (Map<String, String>) params);
        context.getMessage().setPagePayData(data);
        String url = RequestUrlUtils.getPayUrl(context);

        return new TradingResult(TradingStatus.PENDING).
                setRecordId(context.getRecordId()).
                setAmount(context.getOrder().getAmount()).
                setData(url);
    }

    @Override
    public RequestExecutor getRequestExecutor(TradingContext context) {
        return null;
    }

}