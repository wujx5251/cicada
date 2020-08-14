package com.cicada.component.qqpay.interfaces;

import com.cicada.component.qqpay.QqpayAbstractPay;
import com.cicada.component.qqpay.bean.QqResponse;
import com.cicada.component.qqpay.enums.QqpayAction;
import com.cicada.component.qqpay.enums.QqpayParam;
import com.cicada.component.qqpay.enums.QqpayStatus;
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
 * qq支付查询
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.PAY_QUERY, channel = Channel.QQPAY)
public class QqpayQuery extends QqpayAbstractPay {

    @Override
    protected String getAction() {
        return QqpayAction.QUERY.value();
    }

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put(QqpayParam.TRADE_NO.value(), context.getTarget().getRecordId());
        return params;
    }

    @Override
    protected TradingResult render(TradingContext context, Object params) {
        TradingResult result = super.render(context, params);
        result.setRecordId(context.getTarget().getRecordId());
        result.setAmount(context.getTarget().getOrder().getAmount() +
                context.getTarget().getOrder().getRefundAmount());

        QqResponse rsp = (QqResponse) result.getData();

        if (TradingStatus.SUCCESS.equals(result.getStatus())) {
            result.setErrMsg(StringUtils.concat("[", rsp.getStatus(), "]", rsp.getSubMsg()));

            if (ObjectUtils.contains(rsp.getStatus(), QqpayStatus.SUCCESS.value(), QqpayStatus.REFUND.value())) {
                //支付成功或者存在退款既说明原交易支付成功
                result.setStatus(TradingStatus.SUCCESS);
            } else if (ObjectUtils.contains(rsp.getStatus(), QqpayStatus.FAIL.value(), QqpayStatus.REVOKED.value(), QqpayStatus.CLOSED.value())) {
                //支付失败、交易撤销、关闭既为交易失败
                result.setStatus(TradingStatus.FAIL);
            } else if (ObjectUtils.contains(rsp.getStatus(), QqpayStatus.WAIT.value(), QqpayStatus.USERPAYING.value())) {
                result.setStatus(TradingStatus.PENDING);
            }
        } else {
            result.setStatus(context.getTarget().getResult().getStatus());
        }
        return result;
    }
}