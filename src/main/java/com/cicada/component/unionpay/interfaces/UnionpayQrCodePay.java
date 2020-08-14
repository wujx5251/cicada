package com.cicada.component.unionpay.interfaces;

import com.cicada.component.unionpay.UnionpayAbstractReqPay;
import com.cicada.component.unionpay.enums.*;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;

import java.util.Map;

/**
 * 银联二维码支付（被扫）
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.QR_PAY, channel = Channel.UNIONPAY, product = ProductType.QRCODE)
public class UnionpayQrCodePay extends UnionpayAbstractReqPay {

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = super.getParams(context);
        params.put(UnionpayParam.BIZ_TYPE.value(), UnionpayBizType.QUERY_STATUS);
        params.put(UnionpayParam.TXN_TYPE.value(), UnionpayTxnType.CONSUME);
        params.put(UnionpayParam.TXN_SUB_TYPE.value(), UnionpaySubTxnType.QR_CONSUME);

        params.put(UnionpayParam.AMOUNT.value(), context.getOrder().getAmount());
        params.put(UnionpayParam.CURRENCY.value(), UnionpayCurrency.from(context.getOrder().getCurrency()));
        params.put(UnionpayParam.QR_CODE.value(), context.getExtra().getAuthCode());
        return params;
    }

}