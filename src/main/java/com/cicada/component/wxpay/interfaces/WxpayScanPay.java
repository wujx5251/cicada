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
import com.dotin.common.utils.Base64Utils;
import com.dotin.common.utils.QrcodeUtils;

import java.util.Map;

/**
 * 微信-扫描支付主扫
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.SCAN_PAY, channel = Channel.WXPAY, product = ProductType.SCAN)
public class WxpayScanPay extends WxpayAbstractPay {

    @Override
    protected String getTradeType() {
        return WxpayTradeType.NATIVE.value();
    }

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = super.getParams(context);
        params.put(WxpayParam.PRODUCT_ID.value(), context.getRecordId());
        return params;
    }

    @Override
    protected TradingResult render(TradingContext context, TradingResult result) {
        String codeImage = Base64Utils.encode(QrcodeUtils.write2Byte(((WxResponse) result.getData()).getQrCode(), 100));
        return new TradingResult(TradingStatus.PENDING).
                setAmount(context.getOrder().getAmount()).
                setData(codeImage);
    }
}