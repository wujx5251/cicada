package com.cicada.component.unionpay.interfaces;

import com.cicada.component.unionpay.UnionpayAbstractReqPay;
import com.cicada.component.unionpay.bean.UnResponse;
import com.cicada.component.unionpay.enums.*;
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
 * 银联扫描支付（主扫）
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.SCAN_PAY, channel = Channel.UNIONPAY, product = ProductType.SCAN)
public class UnionpayScanPay extends UnionpayAbstractReqPay {

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = super.getParams(context);
        params.put(UnionpayParam.BIZ_TYPE.value(), UnionpayBizType.QUERY_STATUS);
        params.put(UnionpayParam.TXN_TYPE.value(), UnionpayTxnType.CONSUME);
        params.put(UnionpayParam.TXN_SUB_TYPE.value(), UnionpaySubTxnType.QR_APPLY);

        params.put(UnionpayParam.AMOUNT.value(), context.getOrder().getAmount());
        params.put(UnionpayParam.CURRENCY.value(), UnionpayCurrency.from(context.getOrder().getCurrency()));
        return params;
    }

    @Override
    protected TradingResult render(TradingContext context, TradingResult result) {
        String codeImage = Base64Utils.encode(QrcodeUtils.write2Byte(((UnResponse) result.getData()).getQrCode(), 100));
        return new TradingResult(TradingStatus.PENDING).
                setAmount(context.getOrder().getAmount()).
                setData(codeImage);
    }

}