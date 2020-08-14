package com.cicada.component.qqpay.interfaces;

import com.cicada.component.qqpay.QqpayAbstractPay;
import com.cicada.component.qqpay.bean.QqResponse;
import com.cicada.component.qqpay.enums.QqpayParam;
import com.cicada.component.qqpay.enums.QqpayTradeType;
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
 * qq-扫描支付主扫
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.SCAN_PAY, channel = Channel.QQPAY, product = ProductType.SCAN)
public class QqpayScanPay extends QqpayAbstractPay {

    @Override
    protected String getTradeType() {
        return QqpayTradeType.NATIVE.value();
    }

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = super.getParams(context);
        params.put(QqpayParam.PRODUCT_ID.value(), context.getRecordId());
        return params;
    }

    @Override
    protected TradingResult render(TradingContext context, TradingResult result) {
        String codeImage = Base64Utils.encode(QrcodeUtils.write2Byte(((QqResponse) result.getData()).getQrCode(), 100));
        return new TradingResult(TradingStatus.PENDING).
                setAmount(context.getOrder().getAmount()).
                setData(codeImage);
    }
}