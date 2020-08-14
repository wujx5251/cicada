package com.cicada.component.unionpay.interfaces;

import com.cicada.component.unionpay.enums.UnionpayBizType;
import com.cicada.component.unionpay.enums.UnionpayParam;
import com.cicada.component.unionpay.enums.UnionpaySubTxnType;
import com.cicada.component.unionpay.enums.UnionpayTxnType;
import com.cicada.core.bean.TradingCard;
import com.cicada.core.bean.TradingContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 银联代收（托收）
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component
public class UnionpayFetchPay extends UnionpayQuickPay {

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = super.getParams(context);
        params.put(UnionpayParam.BIZ_TYPE.value(), UnionpayBizType.FETCH);
        params.put(UnionpayParam.TXN_TYPE.value(), UnionpayTxnType.FETCH);
        params.put(UnionpayParam.TXN_SUB_TYPE.value(), UnionpaySubTxnType.DEFAULT);
        return params;
    }

    @Override
    protected void smsInfo(Map<String, Object> params, TradingContext target) {

    }
}