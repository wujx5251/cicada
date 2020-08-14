package com.cicada.component.alipay;

import com.cicada.component.alipay.bean.AliResponse;
import com.cicada.component.alipay.bean.AlipayConfig;
import com.cicada.component.alipay.enums.AlipayStatus;
import com.cicada.component.alipay.support.AlipaySecurity;
import com.cicada.core.AbstractPay;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.TradingStatus;
import com.cicada.core.exception.business.IllegalChanelSignatureException;
import com.cicada.core.requester.RequestExecutor;
import com.dotin.common.utils.ObjectUtils;
import com.dotin.common.utils.StringUtils;

import java.util.Map;

/**
 * 支付宝异步通知
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public abstract class AlipayAbstractNotify extends AbstractPay {

    @Override
    public <T> Map<String, T> build(TradingContext context) {
        return null;
    }

    @Override
    public TradingResult render(TradingContext context, Object params) {
        AliResponse rsp = AliResponse.from((Map<String, String>) params);

        TradingResult result = new TradingResult().
                setRetry(context.getConfig().isRetry()).
                setRecordId(context.getRecordId()).
                setReplyId(rsp.getReplyId()).
                setData(rsp);

        if (!AliResponse.SUCCESS.equals(rsp.getCode())) {
            if (ObjectUtils.contains(rsp.getCode(), AliResponse.PENDING)) {
                return result.setStatus(TradingStatus.PENDING).setErrMsg(StringUtils.concat("[", rsp.getCode(), "]", rsp.getMsg()));
            }
            return result.setStatus(TradingStatus.FAIL).setErrMsg(StringUtils.concat("[", rsp.getSubCode(), "]", rsp.getSubMsg()));
        }
        return render(context, result);
    }

    @Override
    protected RequestExecutor getRequestExecutor(TradingContext context) {
        return null;
    }

    @Override
    public TradingResult handleNotify(TradingContext context) {
        Map<String, ?> params = context.getMessage().getOrigin();
        if (!AlipaySecurity.instance.verify((AlipayConfig) context.getConfig(), params)) {
            throw new IllegalChanelSignatureException();
        }
        AliResponse rsp = AliResponse.from((Map<String, String>) params);
        TradingResult result = new TradingResult(TradingStatus.PENDING).
                setRetry(context.getConfig().isRetry()).
                setRecordId(context.getRecordId()).
                setReplyId(rsp.getReplyId()).
                setRspTime(rsp.getPayTime()).
                setData(rsp);
        if (ObjectUtils.contains(rsp.getStatus(), AlipayStatus.SUCCESS.value(), AlipayStatus.FINISHED.value())) {
            result.setStatus(TradingStatus.SUCCESS);
        } else if (AlipayStatus.CLOSED.value().equals(rsp.getStatus())) {
            result.setStatus(TradingStatus.FAIL);
        }
        return result.setData("SUCCESS");
    }

}