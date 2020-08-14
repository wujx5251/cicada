package com.cicada.component.wxpay.interfaces;

import com.cicada.component.wxpay.WxpayAbstractPay;
import com.cicada.component.wxpay.enums.WxpayAction;
import com.cicada.component.wxpay.enums.WxpayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;

import java.util.Map;

/**
 * 微信-二维码支付被扫
 * 用户付款码条形码规则：18位纯数字，以10、11、12、13、14、15开头
 * 如果长时间（建议30秒）都得不到明确状态请调用撤销订单接口。
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.QR_PAY, channel = Channel.WXPAY, product = ProductType.QRCODE)
public class WxpayQrCodePay extends WxpayAbstractPay {

    @Override
    protected String getAction() {
        return WxpayAction.MICRO_PAY.value();
    }

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = super.getParams(context);
        params.put(WxpayParam.AUTH_CODE.value(), context.getExtra().getAuthCode());
        return params;
    }

}