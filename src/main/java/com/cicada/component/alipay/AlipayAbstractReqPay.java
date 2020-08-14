package com.cicada.component.alipay;

import com.cicada.component.alipay.bean.AlipayConfig;
import com.cicada.component.alipay.support.AlipayRequester;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.requester.RequestExecutor;
import com.dotin.common.utils.StringUtils;

/**
 * 支付宝 后台请求支付
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public abstract class AlipayAbstractReqPay extends AlipayAbstractPay {

    @Override
    public RequestExecutor getRequestExecutor(TradingContext context) {
        AlipayConfig config = context.getConfig();
        return new AlipayRequester(StringUtils.concat(config.getGatewayUrl(), "?charset=", config.getCharset()));
    }
}