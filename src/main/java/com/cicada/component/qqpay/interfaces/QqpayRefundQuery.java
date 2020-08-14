package com.cicada.component.qqpay.interfaces;

import com.cicada.component.qqpay.enums.QqpayAction;
import com.cicada.component.qqpay.enums.QqpayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;

import java.util.HashMap;
import java.util.Map;

/**
 * qq退款查询
 * 提交退款申请后，通过调用该接口查询退款状态。
 * 退款有一定延时，用零钱支付的退款20分钟内到账，银行卡支付的退款3个工作日后重新查询退款状态。
 * 如果单个支付订单部分退款次数超过20次请使用退款单号查询
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.REFUND_QUERY, channel = Channel.QQPAY)
public class QqpayRefundQuery extends QqpayQuery {

    @Override
    protected String getAction() {
        return QqpayAction.REFUND_QUERY.value();
    }

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put(QqpayParam.REFUND_NO.value(), context.getTarget().getRecordId());//退款单号
        return params;
    }

    @Override
    protected TradingResult render(TradingContext context, Object params) {
        TradingResult result = super.render(context, params);
        result.setAmount(context.getTarget().getOrder().getRefundAmount());
        return result;
    }
}
