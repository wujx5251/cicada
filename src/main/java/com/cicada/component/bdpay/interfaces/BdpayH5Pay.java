package com.cicada.component.bdpay.interfaces;

import com.cicada.component.bdpay.emuns.BdpayAction;
import com.cicada.core.annotation.Component;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;

/**
 * 百度钱包H5 支付
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.WAP_PAY, channel = Channel.BDPAY, product = ProductType.H5)
public class BdpayH5Pay extends BdpayWebPay {

    @Override
    protected String getAction() {
        return BdpayAction.WAP_PAY.value();
    }
}