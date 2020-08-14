package com.cicada.component.alipay.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.cicada.component.alipay.AlipayAbstractReqPay;
import com.cicada.component.alipay.bean.AliResponse;
import com.cicada.component.alipay.enums.AlipayMethod;
import com.cicada.component.alipay.enums.AlipayParam;
import com.cicada.component.alipay.enums.AlipayStatus;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.TradingStatus;
import com.dotin.common.utils.ObjectUtils;
import com.dotin.common.utils.StringUtils;

/**
 * 支付宝-消费状态查询
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.PAY_QUERY, channel = Channel.ALIPAY)
public class AlipayQuery extends AlipayAbstractReqPay {

    @Override
    protected String getMethod() {
        return AlipayMethod.QUERY.value();
    }

    @Override
    protected JSONObject getBizContent(TradingContext context) {
        JSONObject params = new JSONObject();
        params.put(AlipayParam.TRADE_NO.value(), context.getTarget().getRecordId());//支付交易流水号
        return params;
    }

    @Override
    public TradingResult render(TradingContext context, Object params) {
        TradingResult result = super.render(context, params);
        result.setRecordId(context.getTarget().getRecordId());
        result.setAmount(context.getTarget().getOrder().getAmount() +
                context.getTarget().getOrder().getRefundAmount());

        AliResponse rsp = (AliResponse) result.getData();

        if (TradingStatus.SUCCESS.equals(result.getStatus())) {
            result.setErrMsg(StringUtils.concat("[", rsp.getStatus(), "]", rsp.getSubMsg()));

            if (ObjectUtils.contains(rsp.getStatus(), AlipayStatus.SUCCESS.value(), AlipayStatus.FINISHED.value())) {
                result.setStatus(TradingStatus.SUCCESS);
            } else if (AlipayStatus.CLOSED.value().equals(rsp.getStatus())) {
                result.setStatus(TradingStatus.FAIL);
            } else if (ObjectUtils.contains(rsp.getCode(), AliResponse.PENDING)) {
                result.setStatus(TradingStatus.PENDING);
            }
        } else {
            result.setStatus(context.getTarget().getResult().getStatus());
        }
        return result;
    }

}