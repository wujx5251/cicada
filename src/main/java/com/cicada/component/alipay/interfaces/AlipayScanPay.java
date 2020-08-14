package com.cicada.component.alipay.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.cicada.component.alipay.AlipayAbstractReqPay;
import com.cicada.component.alipay.bean.AliResponse;
import com.cicada.component.alipay.enums.AlipayMethod;
import com.cicada.component.alipay.enums.AlipayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.enums.TradingStatus;
import com.dotin.common.utils.Base64Utils;
import com.dotin.common.utils.QrcodeUtils;

/**
 * 支付宝支付-扫码支付 主扫
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.SCAN_PAY, channel = Channel.ALIPAY, product = ProductType.SCAN)
public class AlipayScanPay extends AlipayAbstractReqPay {

    @Override
    protected String getMethod() {
        return AlipayMethod.SCAN_PAY.value();
    }

    @Override
    protected JSONObject getBizContent(TradingContext context) {
        JSONObject params = super.getBizContent(context);
        params.put(AlipayParam.QR_TIMEOUT.value(), params.get(AlipayParam.TIMEOUT));
        return params;
    }

    @Override
    protected TradingResult render(TradingContext context, TradingResult result) {
        String codeImage = Base64Utils.encode(QrcodeUtils.write2Byte(((AliResponse) result.getData()).getQrCode(), 100));
        return new TradingResult(TradingStatus.PENDING).
                setAmount(context.getOrder().getAmount()).
                setData(codeImage);
    }

}