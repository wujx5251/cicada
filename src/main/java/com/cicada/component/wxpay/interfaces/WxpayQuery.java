package com.cicada.component.wxpay.interfaces;

import com.cicada.component.wxpay.WxpayAbstractPay;
import com.cicada.component.wxpay.bean.WxResponse;
import com.cicada.component.wxpay.enums.WxpayAction;
import com.cicada.component.wxpay.enums.WxpayParam;
import com.cicada.component.wxpay.enums.WxpayStatus;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.TradingStatus;
import com.dotin.common.utils.ObjectUtils;
import com.dotin.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付查询
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.PAY_QUERY, channel = Channel.WXPAY)
public class WxpayQuery extends WxpayAbstractPay {

    @Override
    protected String getAction() {
        return WxpayAction.QUERY.value();
    }

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put(WxpayParam.TRADE_NO.value(), context.getTarget().getRecordId());
        return params;
    }

    @Override
    protected TradingResult render(TradingContext context, Object params) {
        TradingResult result = super.render(context, params);
        result.setRecordId(context.getTarget().getRecordId());
        result.setAmount(context.getTarget().getOrder().getAmount() +
                context.getTarget().getOrder().getRefundAmount());

        WxResponse rsp = (WxResponse) result.getData();

        if (TradingStatus.SUCCESS.equals(result.getStatus())) {
            result.setErrMsg(StringUtils.concat("[", rsp.getStatus(), "]", rsp.getSubMsg()));

            if (ObjectUtils.contains(rsp.getStatus(), WxpayStatus.SUCCESS.value(), WxpayStatus.REFUND.value())) {
                //支付成功或者存在退款既说明原交易支付成功
                result.setStatus(TradingStatus.SUCCESS);
            } else if (ObjectUtils.contains(rsp.getStatus(), WxpayStatus.FAIL.value(), WxpayStatus.REVOKED.value(), WxpayStatus.CLOSED.value())) {
                //支付失败、交易撤销、关闭既为交易失败
                result.setStatus(TradingStatus.FAIL);
            } else if (ObjectUtils.contains(rsp.getStatus(), WxpayStatus.WAIT.value(), WxpayStatus.USERPAYING.value())) {
                result.setStatus(TradingStatus.PENDING);
            }
        } else {
            result.setStatus(context.getTarget().getResult().getStatus());
        }
        return result;
    }
}