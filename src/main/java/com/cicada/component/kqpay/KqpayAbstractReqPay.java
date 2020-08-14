package com.cicada.component.kqpay;

import com.cicada.component.kqpay.bean.KqPartObject;
import com.cicada.component.kqpay.bean.KqpayConfig;
import com.cicada.component.kqpay.enums.KqpayParam;
import com.cicada.component.kqpay.support.KqpayRequester;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.requester.RequestExecutor;
import com.cicada.utils.PayUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 快钱 后台请求支付
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public abstract class KqpayAbstractReqPay extends KqpayAbstractPay {

    /**
     * 获取节点名称
     *
     * @return
     */
    abstract protected String getNodeName();

    @Override
    public Map<String, Object> build(TradingContext context) {
        KqpayConfig config = context.getConfig();
        Map<String, Object> params = new HashMap<String, Object>(25);
        params.put(KqpayParam.VERSION.value(), config.getVersion());
        params.put(getNodeName(), getParam(context));
        return params;
    }

    /**
     * 获取交易参数
     *
     * @param context
     * @return
     */
    protected KqPartObject getParam(TradingContext context) {
        KqpayConfig config = context.getConfig();
        String cardNo = getShortCardNo(context);
        KqPartObject param = KqPartObject.instance().
                put(KqpayParam.MERC_ID.value(), config.getMchId()).
                put(KqpayParam.TERM_ID.value(), config.getTerminalId()).
                put(KqpayParam.TRADE_NO.value(), context.getRecordId()).
                put(KqpayParam.CUSTOMER_ID.value(), getToken(context)).
                put(KqpayParam.SHORT_ACC_NO.value(), cardNo).
                put(KqpayParam.SHORT_CARD_NO.value(), cardNo);
        return param;
    }

    /**
     * 获取短卡号
     *
     * @param context
     * @return
     */
    protected String getShortCardNo(TradingContext context) {
        if (null != context.getCard())
            return PayUtils.getShortCardNo(context.getCard().getNo());
        return null;
    }

    /**
     * 获取绑卡token
     *
     * @param context
     * @return
     */
    protected String getToken(TradingContext context) {
        if (null != context.getCard())
            return context.getCard().getToken();
        return null;
    }

    @Override
    public RequestExecutor getRequestExecutor(TradingContext context) {
        KqpayConfig config = context.getConfig();
        return new KqpayRequester(config.getGatewayUrl() + getAction());
    }

}