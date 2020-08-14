package com.cicada.component.unionpay;

import com.cicada.component.unionpay.bean.UnResponse;
import com.cicada.component.unionpay.bean.UnionpayConfig;
import com.cicada.component.unionpay.support.UnionpaySecurity;
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
 * 银联异步通知
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public abstract class UnionpayAbstractNotify extends AbstractPay {

    @Override
    public <T> Map<String, T> build(TradingContext context) {
        return null;
    }

    @Override
    public TradingResult render(TradingContext context, Object params) {
        TradingResult result = handle(context, params);
        if (null == result.getStatus()) {
            result = render(context, result);
            result.setStatus(TradingStatus.PENDING);
        }
        return result;
    }

    private TradingResult handle(TradingContext context, Object params) {
        UnResponse rsp = UnResponse.from((Map<String, String>) params);

        TradingResult result = new TradingResult().
                setRetry(context.getConfig().isRetry()).
                setRecordId(context.getRecordId()).
                setReplyId(rsp.getReplyId()).
                setData(rsp);

        if (!UnResponse.SUCCESS.equals(rsp.getCode())) {
            String message = StringUtils.concat("[", rsp.getCode(), "]", rsp.getMsg());
            if (ObjectUtils.contains(rsp.getCode(), UnResponse.PENDING)) {
                return result.setStatus(TradingStatus.PENDING).setErrMsg(message);
            }
            return result.setStatus(TradingStatus.FAIL).setErrMsg(message);
        }
        return result;
    }

    @Override
    protected RequestExecutor getRequestExecutor(TradingContext context) {
        return null;
    }

    @Override
    public TradingResult handleNotify(TradingContext context) {
        Map<String, Object> params = context.getMessage().getOrigin();
        if (!UnionpaySecurity.instance.verify((UnionpayConfig) context.getConfig(), params)) {
            throw new IllegalChanelSignatureException();
        }
        TradingResult result = handle(context, params);
        if (null == result.getStatus())
            result.setStatus(TradingStatus.SUCCESS);
        return result.setData("SUCCESS");
    }
}