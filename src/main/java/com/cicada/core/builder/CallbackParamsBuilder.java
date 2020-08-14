package com.cicada.core.builder;

import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingParameter;
import com.dotin.common.utils.DateUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WUJXIAO
 * @version 1.0
 * @create 2019-01-02 20:10
 */
@Component
public class CallbackParamsBuilder {

    /**
     * 生成回调参数
     *
     * @param context
     * @return
     */
    public Map<String, Object> build(TradingContext context) {
        Map<String, Object> params = new HashMap<String, Object>(15);
        params.put(TradingParameter.METHOD, context.getMethod());
        params.put(TradingParameter.VERSION, context.getVersion());
        params.put(TradingParameter.CHANNEL, context.getChannel().value());
        params.put(TradingParameter.RECORD_ID, context.getRecordId());
        params.put(TradingParameter.REPLY_ID, context.getResult().getReplyId());
        params.put(TradingParameter.Order.ID, context.getOrder().getOrderId());
        params.put(TradingParameter.Order.TIME, DateUtils.format(context.getOrder().getOrderTime()));
        params.put(TradingParameter.Order.AMOUNT, context.getOrder().getAmount() + context.getOrder().getRefundAmount());
        params.put(TradingParameter.STATUS, context.getResult().getStatus().name());
        params.put(TradingParameter.RESULT_MSG, context.getResult().getErrMsg());
        params.put(TradingParameter.RESERVED, context.getMessage().getReqReserved());
        return params;
    }


}