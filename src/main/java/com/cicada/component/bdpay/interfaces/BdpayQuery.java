package com.cicada.component.bdpay.interfaces;

import com.cicada.component.bdpay.bean.BdResponse;
import com.cicada.component.bdpay.bean.BdpayConfig;
import com.cicada.component.bdpay.emuns.BdpayAction;
import com.cicada.component.bdpay.emuns.BdpayParam;
import com.cicada.component.bdpay.emuns.BdpayService;
import com.cicada.component.bdpay.emuns.BdpayStatus;
import com.cicada.component.bdpay.support.BdpayRequester;
import com.cicada.component.bdpay.support.BdpaySecurity;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.TradingStatus;
import com.cicada.core.requester.RequestExecutor;

import java.util.HashMap;
import java.util.Map;

/**
 * 百度钱包交易查询
 *
 * @author: WUJXIAO
 * @create: 2019-01-26 10:58
 * @version: 1.0
 **/
@Component(value = ComponetType.QUERY, channel = Channel.BDPAY)
public class BdpayQuery extends BdpayRefund {

    @Override
    protected String getAction() {
        return BdpayAction.QUERY.value();
    }

    @Override
    public Map<String, ?> build(TradingContext context) {
        BdpayConfig config = context.getConfig();
        Map<String, Object> params = new HashMap<String, Object>(8);
        params.put(BdpayParam.VERSION.value(), config.getVersion());
        params.put(BdpayParam.CODE.value(), BdpayService.QUERY);
        params.put(BdpayParam.MCH_ID.value(), config.getMchId());
        params.put(BdpayParam.TRADE_NO.value(), context.getTarget().getRecordId());
        params.put(BdpayParam.OUT_CHARSET.value(), config.getFormat());
        params.put(BdpayParam.FORMAT.value(), config.getCharset());
        params.put(BdpayParam.SIGN_TYPE.value(), config.getSignType());

        String sign = BdpaySecurity.instance.sign(config, params);
        params.put(BdpayParam.SIGN.value(), sign);
        return params;
    }

    @Override
    public TradingResult render(TradingContext context, Object params) {
        BdResponse rsp = BdResponse.from((Map<String, String>) params);

        TradingResult result = new TradingResult().
                setRetry(context.getConfig().isRetry()).
                setRecordId(context.getRecordId()).
                setReplyId(rsp.getReplyId()).
                setData(rsp);

        BdpayStatus payStatus = BdpayStatus.from(rsp.getPayStatus());
        BdpayStatus status = BdpayStatus.from(rsp.getQueryStatus());

        if (BdResponse.SUCCESS.equals(rsp.getQueryStatus())) {
            if (BdResponse.SUCCESS.equals(payStatus) || BdResponse.REFUND.equals(payStatus)) {
                return result.setStatus(TradingStatus.SUCCESS);
            } else if (BdResponse.PENDING.equals(rsp.getPayStatus())) {
                return result.setStatus(TradingStatus.PENDING);
            } else {
                return result.setStatus(TradingStatus.FAIL).setErrMsg(payStatus.desc());
            }
        } else {
            return result.setStatus(TradingStatus.PENDING).setErrMsg(status.desc());
        }
    }

    @Override
    protected RequestExecutor getRequestExecutor(TradingContext context) {
        BdpayConfig config = context.getConfig();
        return new BdpayRequester(config.getGatewayUrl() + getAction());
    }
}