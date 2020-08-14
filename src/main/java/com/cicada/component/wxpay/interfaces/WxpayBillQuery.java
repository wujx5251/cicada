package com.cicada.component.wxpay.interfaces;

import com.cicada.component.wxpay.WxpayAbstractPay;
import com.cicada.component.wxpay.enums.WxpayAction;
import com.cicada.component.wxpay.enums.WxpayParam;
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
 * 微信-对账单查询
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.BILL_DOWN, channel = Channel.WXPAY)
public class WxpayBillQuery extends WxpayAbstractPay {

    @Override
    protected String getAction() {
        return WxpayAction.BILL.value();
    }

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = new HashMap<String, Object>(3);
        params.put(WxpayParam.BILL_DATE.value(), DateUtils.format(context.getExtra().getBillDate(), DateUtils.DATE_SHORT_FORMAT));
        params.put(WxpayParam.BILL_TYPE.value(), "ALL");
        params.put(WxpayParam.BILL_RESULT_TYPE.value(), "GZIP");
        return params;
    }

    @Override
    protected TradingResult render(TradingContext context, Object rsp) {
        return new TradingResult(TradingStatus.SUCCESS).setData(ZipUtils.decompress((byte[]) rsp));
    }

}