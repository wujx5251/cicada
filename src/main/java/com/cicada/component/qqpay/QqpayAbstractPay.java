package com.cicada.component.qqpay;

import com.cicada.component.qqpay.bean.QqpayConfig;
import com.cicada.component.qqpay.enums.QqpayAction;
import com.cicada.component.qqpay.enums.QqpayParam;
import com.cicada.component.qqpay.support.QqpayRequester;
import com.cicada.component.qqpay.support.QqpaySecurity;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.generate.Generator;
import com.cicada.core.requester.RequestExecutor;
import com.cicada.utils.RequestUrlUtils;
import com.dotin.common.utils.DateUtils;
import com.dotin.common.utils.LocalUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * qq
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public abstract class QqpayAbstractPay extends QqpayAbstractNotify {

    protected String getTradeType() {
        return null;
    }

    protected String getAction() {
        return QqpayAction.CREATE_ORDER.value();
    }

    @Autowired
    private Generator generator;

    @Override
    public TradingResult doPay(TradingContext context) {
        QqpayConfig config = context.getConfig();
        return super.doPay(context);
    }

    @Override
    public Map<String, ?> build(TradingContext context) {
        QqpayConfig config = context.getConfig();
        Map<String, Object> params = new HashMap<String, Object>(16);
        params.put(QqpayParam.APP_ID.value(), config.getAppId());
        params.put(QqpayParam.MCH_ID.value(), config.getMchId());
        params.put(QqpayParam.TRADE_TYPE.value(), getTradeType());
        params.put(QqpayParam.NONCE_STR.value(), generator.generateSalt(16));
        params.put(QqpayParam.SIGN_TYPE.value(), config.getSignType());
        Map<String, Object> ext = getParams(context);
        if (null != ext)
            params.putAll(ext);

        String sign = QqpaySecurity.instance.sign(config, params);
        params.put(QqpayParam.SIGN.value(), sign);
        return params;
    }

    protected Map<String, Object> getParams(TradingContext context) {
        QqpayConfig config = context.getConfig();
        Map<String, Object> params = new HashMap<String, Object>(6);
        params.put(QqpayParam.NOTIFY_URL.value(), RequestUrlUtils.getNotifyUrl(context));
        params.put(QqpayParam.CLIENT_IP.value(), LocalUtil.LOCAL_IP);
        params.put(QqpayParam.TRADE_NO.value(), context.getRecordId());
        params.put(QqpayParam.AMOUNT.value(), context.getOrder().getAmount());
        params.put(QqpayParam.CURRENCY.value(), context.getOrder().getCurrency());
        params.put(QqpayParam.SUBJECT.value(), context.getOrder().getSubject());
        params.put(QqpayParam.STRAT_TIME.value(), DateUtils.format(context.getCreateTime(),
                DateUtils.DATE_TIME_SHORT_FORMAT));
        params.put(QqpayParam.TIMEOUT.value(),
                DateUtils.format(context.getCreateTime() + config.getTimeout(),
                        DateUtils.DATE_TIME_SHORT_FORMAT));
        return params;
    }

    @Override
    public RequestExecutor getRequestExecutor(TradingContext context) {
        QqpayConfig config = context.getConfig();
        return new QqpayRequester(config.getGatewayUrl() + getAction());
    }
}