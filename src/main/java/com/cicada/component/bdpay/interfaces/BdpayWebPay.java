package com.cicada.component.bdpay.interfaces;

import com.cicada.component.bdpay.BdpayAbstractPay;
import com.cicada.component.bdpay.bean.BdpayConfig;
import com.cicada.component.bdpay.emuns.BdpayAction;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.enums.TradingStatus;
import com.cicada.utils.PayUtils;
import com.cicada.utils.RequestUrlUtils;
import com.dotin.common.base.Charsets;

import java.util.Map;

/**
 * 百度钱包Web 支付
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.WEB_PAY, channel = Channel.BDPAY, product = ProductType.WEB)
public class BdpayWebPay extends BdpayAbstractPay {

    protected String getAction() {
        return BdpayAction.WEB_PAY.value();
    }

    @Override
    public TradingResult render(TradingContext context, Object params) {
        BdpayConfig config = context.getConfig();
        String data = PayUtils.generateHtml(config.getGatewayUrl() + getAction(), (Map<String, String>) params, "GET", Charsets.GBK);
        context.getMessage().setPagePayData(data);
        String url = RequestUrlUtils.getPayUrl(context);

        return new TradingResult(TradingStatus.PENDING).
                setRecordId(context.getRecordId()).
                setAmount(context.getOrder().getAmount()).
                setData(url);
    }
}