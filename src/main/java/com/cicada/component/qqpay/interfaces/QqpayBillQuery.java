package com.cicada.component.qqpay.interfaces;

import com.cicada.component.qqpay.QqpayAbstractPay;
import com.cicada.component.qqpay.enums.QqpayAction;
import com.cicada.component.qqpay.enums.QqpayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.TradingStatus;
import com.dotin.common.utils.DateUtils;
import com.dotin.common.utils.ZipUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * qq-对账单查询
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.BILL_DOWN, channel = Channel.QQPAY)
public class QqpayBillQuery extends QqpayAbstractPay {

    @Override
    protected String getAction() {
        return QqpayAction.BILL.value();
    }

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = new HashMap<String, Object>(3);
        params.put(QqpayParam.BILL_DATE.value(), DateUtils.format(context.getExtra().getBillDate(), DateUtils.DATE_SHORT_FORMAT));
        params.put(QqpayParam.BILL_TYPE.value(), "ALL");
        params.put(QqpayParam.BILL_RESULT_TYPE.value(), "GZIP");
        return params;
    }

    @Override
    protected TradingResult render(TradingContext context, Object rsp) {
        return new TradingResult(TradingStatus.SUCCESS).setData(ZipUtils.decompress((byte[]) rsp));
    }

}