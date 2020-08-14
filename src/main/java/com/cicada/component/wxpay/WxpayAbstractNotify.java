package com.cicada.component.wxpay;

import com.cicada.component.wxpay.bean.WxResponse;
import com.cicada.component.wxpay.bean.WxpayConfig;
import com.cicada.component.wxpay.support.WxpaySecurity;
import com.cicada.core.AbstractPay;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.TradingStatus;
import com.cicada.core.exception.business.IllegalChanelSignatureException;
import com.cicada.core.requester.RequestExecutor;
import com.dotin.common.utils.ObjectUtils;
import com.dotin.common.utils.StringUtils;
import com.dotin.common.utils.XmlUtils;

import java.util.Map;

/**
 * 微信异步通知
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public abstract class WxpayAbstractNotify extends AbstractPay {

    @Override
    public <T> Map<String, T> build(TradingContext context) {
        return null;
    }

    @Override
    protected TradingResult render(TradingContext context, Object params) {
        TradingResult result = handle(context, params);
        if (null == result.getStatus() || TradingStatus.SUCCESS.equals(result.getStatus())) {
            return render(context, result);
        }
        return result;
    }

    private TradingResult handle(TradingContext context, Object params) {
        WxResponse rsp = WxResponse.from((Map<String, String>) params);

        TradingResult result = new TradingResult().
                setRetry(context.getConfig().isRetry()).
                setRecordId(context.getRecordId()).
                setReplyId(rsp.getReplyId()).
                setData(rsp);

        if (!WxResponse.SUCCESS.equals(rsp.getCode())) {
            return result.setStatus(TradingStatus.FAIL).setErrMsg(StringUtils.concat("[", rsp.getCode(), "]", rsp.getMsg()));
        } else if (null != rsp.getSubCode() && !WxResponse.SUCCESS.equals(rsp.getSubCode())) {
            if (ObjectUtils.contains(rsp.getSubCode(), WxResponse.PENDING)) {
                return result.setStatus(TradingStatus.PENDING).setErrMsg(StringUtils.concat("[", rsp.getSubCode(), "]", rsp.getErrMsg()));
            }
            return result.setStatus(TradingStatus.FAIL).setErrMsg(StringUtils.concat("[", rsp.getErrCode(), "]", rsp.getErrMsg()));
        }
        return result;
    }

    @Override
    protected RequestExecutor getRequestExecutor(TradingContext context) {
        return null;
    }

    @Override
    public TradingResult handleNotify(TradingContext context) {
        Map<String, Object> params = XmlUtils.toMap(context.getMessage().getAgentRequestData());
        if (!WxpaySecurity.instance.verify((WxpayConfig) context.getConfig(), params)) {
            throw new IllegalChanelSignatureException();
        }
        TradingResult result = handle(context, params);
        result.setRspTime(((WxResponse) result.getData()).getPayTime());
        if (null == result.getStatus()) {
            result.setStatus(TradingStatus.SUCCESS);
        }
        return result.setData("<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>");
    }

}