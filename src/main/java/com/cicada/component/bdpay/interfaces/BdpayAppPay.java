package com.cicada.component.bdpay.interfaces;

import com.cicada.component.bdpay.BdpayAbstractPay;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.enums.TradingStatus;
import com.cicada.utils.PayUtils;
import com.dotin.common.base.Charsets;

import java.util.Map;

/**
 * 百度钱包app 支付
 *
 * @author: WUJXIAO
 * @create: 2019-01-26 10:58
 * @version: 1.0
 **/
@Component(value = ComponetType.APP_PAY, channel = Channel.BDPAY, product = ProductType.APP)
public class BdpayAppPay extends BdpayAbstractPay {

    @Override
    public TradingResult render(TradingContext context, Object params) {
        String data = PayUtils.getQueryStr((Map<String, String>) params, true, Charsets.GBK);

        return new TradingResult(TradingStatus.PENDING).
                setRecordId(context.getRecordId()).
                setAmount(context.getOrder().getAmount()).
                setData(data);
    }
}