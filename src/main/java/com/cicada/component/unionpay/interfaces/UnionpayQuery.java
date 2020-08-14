package com.cicada.component.unionpay.interfaces;

import com.cicada.component.unionpay.UnionpayAbstractReqPay;
import com.cicada.component.unionpay.bean.UnResponse;
import com.cicada.component.unionpay.enums.UnionpayBizType;
import com.cicada.component.unionpay.enums.UnionpayParam;
import com.cicada.component.unionpay.enums.UnionpaySubTxnType;
import com.cicada.component.unionpay.enums.UnionpayTxnType;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.TradingStatus;
import com.dotin.common.utils.DateUtils;
import com.dotin.common.utils.ObjectUtils;
import com.dotin.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 银联支付查询
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.QUERY, channel = Channel.UNIONPAY)
public class UnionpayQuery extends UnionpayAbstractReqPay {

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = new HashMap<String, Object>(5);
        params.put(UnionpayParam.BIZ_TYPE.value(), UnionpayBizType.QUERY_STATUS);
        params.put(UnionpayParam.TXN_TYPE.value(), UnionpayTxnType.QUERY_STATUS);
        params.put(UnionpayParam.TXN_SUB_TYPE.value(), UnionpaySubTxnType.DEFAULT);

        params.put(UnionpayParam.TRADE_NO.value(), context.getTarget().getRecordId());
        params.put(UnionpayParam.TIMESTAMP.value(),
                DateUtils.format(context.getTarget().getCreateTime(), DateUtils.DATE_TIME_SHORT_FORMAT));//原单时间
        return params;
    }

    @Override
    public TradingResult render(TradingContext context, Object params) {
        TradingResult result = super.render(context, params);
        result.setRecordId(context.getTarget().getRecordId());
        result.setAmount(context.getTarget().getOrder().getAmount() +
                context.getTarget().getOrder().getRefundAmount());

        UnResponse rsp = (UnResponse) result.getData();

        if (TradingStatus.SUCCESS.equals(result.getStatus())) {
            result.setErrMsg(StringUtils.concat("[", rsp.getSubCode(), "]", rsp.getSubMsg()));

            if (UnResponse.SUCCESS.equals(rsp.getSubCode())) {
                result.setStatus(TradingStatus.SUCCESS);
            } else if (!ObjectUtils.contains(rsp.getSubCode(), UnResponse.PENDING)) {
                result.setStatus(TradingStatus.FAIL);
            } else if (ObjectUtils.contains(rsp.getCode(), UnResponse.PENDING)) {
                result.setStatus(TradingStatus.PENDING);
            }
        } else {
            result.setStatus(context.getTarget().getResult().getStatus());
        }
        return result;
    }

}