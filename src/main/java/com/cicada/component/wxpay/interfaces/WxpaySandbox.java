package com.cicada.component.wxpay.interfaces;

import com.cicada.component.wxpay.WxpayAbstractPay;
import com.cicada.component.wxpay.enums.WxpayAction;
import org.springframework.stereotype.Component;

/**
 * 微信沙箱信息获取
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component
public class WxpaySandbox extends WxpayAbstractPay {

    @Override
    protected String getAction() {
        return WxpayAction.SANDBOX.value();
    }

}
