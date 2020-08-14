package com.cicada.component.bdpay;

import com.cicada.component.bdpay.bean.BdpayConfig;
import com.cicada.component.bdpay.emuns.BdpayCurrency;
import com.cicada.component.bdpay.emuns.BdpayParam;
import com.cicada.component.bdpay.emuns.BdpayPayType;
import com.cicada.component.bdpay.emuns.BdpayService;
import com.cicada.component.bdpay.support.BdpaySecurity;
import com.cicada.core.bean.TradingContext;
import com.cicada.utils.RequestUrlUtils;
import com.dotin.common.utils.DateUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 百度钱包
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public abstract class BdpayAbstractPay extends BdpayAbstractNotify {

    @Override
    public Map<String, ?> build(TradingContext context) {
        BdpayConfig config = context.getConfig();
        Map<String, Object> params = new HashMap<String, Object>(18);
        params.put(BdpayParam.VERSION.value(), config.getVersion());
        params.put(BdpayParam.CODE.value(), BdpayService.PAY);
        params.put(BdpayParam.MCH_ID.value(), config.getMchId());
        params.put(BdpayParam.TRADE_NO.value(), context.getRecordId());
        params.put(BdpayParam.TRADE_TIME.value(), DateUtils.format(context.getCreateTime(), DateUtils.DATE_TIME_SHORT_FORMAT));
        params.put(BdpayParam.AMOUNT.value(), context.getOrder().getAmount());
        params.put(BdpayParam.CURRENCY.value(), BdpayCurrency.from(context.getOrder().getCurrency()));
        params.put(BdpayParam.SUBJECT.value(), context.getOrder().getSubject());
        params.put(BdpayParam.BODY.value(), context.getOrder().getBody());
        params.put(BdpayParam.PAY_TYPE.value(), BdpayPayType.E_PAY);
        if (null != context.getCard()) {
            params.put(BdpayParam.BANK_ID.value(), context.getCard().getOrgCode());
        }
        params.put(BdpayParam.EXPIRE.value(), DateUtils.format(config.getLastTime(), DateUtils.DATE_TIME_SHORT_FORMAT));
        params.put(BdpayParam.NOTIFY_URL.value(), RequestUrlUtils.getNotifyUrl(context));
        params.put(BdpayParam.RETURN_URL.value(), RequestUrlUtils.getReturnUrl(context));
        params.put(BdpayParam.CHARSET.value(), config.getCharset());
        params.put(BdpayParam.SIGN_TYPE.value(), config.getSignType());

        String sign = BdpaySecurity.instance.sign(config, params);
        params.put(BdpayParam.SIGN.value(), sign);
        return params;
    }

}