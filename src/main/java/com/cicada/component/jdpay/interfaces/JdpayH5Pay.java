package com.cicada.component.jdpay.interfaces;

import com.cicada.core.annotation.Component;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;

/**
 * 京东H5 支付
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.WAP_PAY, channel = Channel.JDPAY, product = ProductType.H5)
public class JdpayH5Pay extends JdpayWebPay {
}