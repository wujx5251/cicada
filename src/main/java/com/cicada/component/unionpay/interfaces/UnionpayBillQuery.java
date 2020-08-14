package com.cicada.component.unionpay.interfaces;

import com.cicada.component.unionpay.UnionpayAbstractReqPay;
import com.cicada.component.unionpay.enums.UnionpayBizType;
import com.cicada.component.unionpay.enums.UnionpayParam;
import com.cicada.component.unionpay.enums.UnionpaySubTxnType;
import com.cicada.component.unionpay.enums.UnionpayTxnType;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.dotin.common.utils.DateUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 银联支付撤销
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.BILL_DOWN, channel = Channel.UNIONPAY)
public class UnionpayBillQuery extends UnionpayAbstractReqPay {

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = new HashMap<String, Object>(6);
        params.put(UnionpayParam.BIZ_TYPE.value(), UnionpayBizType.QUERY_STATUS);
        params.put(UnionpayParam.TXN_TYPE.value(), UnionpayTxnType.BILL);
        params.put(UnionpayParam.TXN_SUB_TYPE.value(), UnionpaySubTxnType.DEFAULT);

        params.put(UnionpayParam.BILL_DATE.value(), context.getTarget().getOrder().getAmount());
        params.put(UnionpayParam.BILL_FILE_TYPE.value(), "00");
        params.put(UnionpayParam.TIMESTAMP.value(), DateUtils.format(context.getCreateTime(),
                DateUtils.DATE_TIME_SHORT_FORMAT));
        return params;
    }

}