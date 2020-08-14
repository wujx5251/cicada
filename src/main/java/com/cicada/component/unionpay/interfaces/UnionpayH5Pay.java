package com.cicada.component.unionpay.interfaces;

import com.cicada.core.annotation.Component;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;

/**
 * 银联 H5支付
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.WAP_PAY, channel = Channel.UNIONPAY, product = ProductType.H5)
public class UnionpayH5Pay extends UnionpayWebPay {

}
