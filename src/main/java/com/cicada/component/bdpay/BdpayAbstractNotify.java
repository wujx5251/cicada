package com.cicada.component.bdpay;

import com.alibaba.fastjson.JSON;
import com.cicada.component.bdpay.bean.BdResponse;
import com.cicada.component.bdpay.bean.BdpayConfig;
import com.cicada.component.bdpay.emuns.BdpayStatus;
import com.cicada.component.bdpay.support.BdpaySecurity;
import com.cicada.core.AbstractPay;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.TradingStatus;
import com.cicada.core.exception.business.IllegalChanelSignatureException;
import com.cicada.core.requester.RequestExecutor;

import java.util.Map;

/**
 * 百度钱包异步通知
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public abstract class BdpayAbstractNotify extends AbstractPay {

    @Override
    public <T> Map<String, T> build(TradingContext context) {
        return null;
    }

    @Override
    protected TradingResult render(TradingContext context, Object params) {
        TradingResult result = handle(context, params);
        if (null == result.getStatus() || TradingStatus.SUCCESS.equals(result.getStatus())){
            return render(context, result);
        }
        return result;
    }

    protected TradingResult handle(TradingContext context, Object params) {
        BdResponse rsp = BdResponse.from((Map<String, String>) params);

        TradingResult result = new TradingResult().
                setRetry(context.getConfig().isRetry()).
                setRecordId(context.getRecordId()).
                setReplyId(rsp.getReplyId()).
                setData(rsp);

        BdpayStatus status = BdpayStatus.from(rsp.getPayStatus());
        if (BdpayStatus.SUCCESS.equals(status)) {
            return result.setStatus(TradingStatus.SUCCESS);
        } else if (BdpayStatus.PENDING.equals(status)) {
            return result.setStatus(TradingStatus.PENDING);
        } else {
            return result.setStatus(TradingStatus.FAIL).setErrMsg(status.desc());
        }
    }

    @Override
    protected RequestExecutor getRequestExecutor(TradingContext context) {
        return null;
    }

    @Override
    public TradingResult handleNotify(TradingContext context) {
        Map<String, Object> params = JSON.parseObject(context.getMessage().getAgentRequestData(), Map.class);
        if (!BdpaySecurity.instance.verify((BdpayConfig) context.getConfig(), params)) {
            throw new IllegalChanelSignatureException();
        }
        TradingResult result = handle(context, params);
        if (null == result.getStatus())
            result.setStatus(TradingStatus.SUCCESS);
        return result.setData("<html><head><meta name=\"VIP_BFB_PAYMENT\" content=\"BAIFUBAO\"></head></html>");
    }

}