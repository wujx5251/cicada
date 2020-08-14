package com.cicada.component.unionpay;

import com.cicada.component.unionpay.bean.UnionpayConfig;
import com.cicada.component.unionpay.enums.UnionpayAction;
import com.cicada.component.unionpay.enums.UnionpayParam;
import com.cicada.component.unionpay.support.UnionpayRequester;
import com.cicada.component.unionpay.support.UnionpaySecurity;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.requester.RequestExecutor;
import com.cicada.utils.RequestUrlUtils;
import com.dotin.common.utils.DateUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 银联
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public abstract class UnionpayAbstractReqPay extends UnionpayAbstractNotify {

    /**
     * 银联接口，默认后台支付
     *
     * @return
     */
    protected String getAction() {
        return UnionpayAction.BACK_REQ.value();
    }

    @Override
    public Map<String, ?> build(TradingContext context) {
        UnionpayConfig config = context.getConfig();
        Map<String, Object> params = new HashMap<String, Object>(25);
        params.put(UnionpayParam.VERSION.value(), config.getVersion());
        params.put(UnionpayParam.CHARSET.value(), config.getCharset());
        params.put(UnionpayParam.CERT_ID.value(), config.getMchCert().getCertNo());

        params.put(UnionpayParam.CHANNEL_TYPE.value(), config.getChannelType());
        params.put(UnionpayParam.ACCESS_TYPE.value(), config.getAccessType());//接入类型
        params.put(UnionpayParam.MER_ID.value(), config.getMchId());
        params.put(UnionpayParam.NOTIFY_URL.value(), RequestUrlUtils.getNotifyUrl(context));
        params.put(UnionpayParam.MER_ID.value(), config.getMchId());
        params.put(UnionpayParam.ACC_TYPE.value(), "01");//银行卡
        if (null != context.getCard())
            params.put(UnionpayParam.CARD_TYPE.value(), context.getCard().getType());

        Map<String, Object> ext = getParams(context);
        if (null != ext)
            params.putAll(ext);

        params.put(UnionpayParam.SIGN_TYPE.value(), config.getSignType());
        String sign = UnionpaySecurity.instance.sign(config, params);
        params.put(UnionpayParam.SIGN.value(), sign);
        return params;
    }


    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = new HashMap<String, Object>(12);
        params.put(UnionpayParam.TRADE_NO.value(), context.getRecordId());
        params.put(UnionpayParam.TIMESTAMP.value(), DateUtils.format(context.getCreateTime(),
                DateUtils.DATE_TIME_SHORT_FORMAT));
        return params;
    }



    @Override
    protected RequestExecutor getRequestExecutor(TradingContext context) {
        UnionpayConfig config = context.getConfig();
        return new UnionpayRequester(config.getGatewayUrl() + getAction());
    }

}