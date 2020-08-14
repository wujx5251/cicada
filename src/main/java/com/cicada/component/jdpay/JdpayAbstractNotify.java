package com.cicada.component.jdpay;

import com.cicada.component.jdpay.bean.JdResponse;
import com.cicada.component.jdpay.bean.JdpayConfig;
import com.cicada.component.jdpay.emuns.JdpayParam;
import com.cicada.component.jdpay.support.JdpayResultParser;
import com.cicada.component.jdpay.support.JdpaySecurity;
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
 * 京东支付异步通知
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public abstract class JdpayAbstractNotify extends AbstractPay {

    private final static JdpayResultParser parser = new JdpayResultParser();

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

    private TradingResult handle(TradingContext context, Object params) {
        JdResponse rsp = JdResponse.from((Map<String, String>) params);

        TradingResult result = new TradingResult().
                setRetry(context.getConfig().isRetry()).
                setRecordId(context.getRecordId()).
                setReplyId(rsp.getReplyId()).
                setData(rsp);

        if (!JdResponse.SUCCESS.equals(rsp.getCode())) {
            return result.setStatus(TradingStatus.FAIL).setErrMsg(StringUtils.concat("[", rsp.getCode(), "]", rsp.getMsg()));
        } else if (null != rsp.getStatus()) {
            if (ObjectUtils.contains(rsp.getStatus(), JdResponse.PENDING)) {
                return result.setStatus(TradingStatus.PENDING).setErrMsg(StringUtils.concat("[", rsp.getStatus(), "]", rsp.getMsg()));
            } else if (JdResponse.OK.equals(rsp.getStatus())) {
                return result.setStatus(TradingStatus.SUCCESS);
            }
            return result.setStatus(TradingStatus.FAIL).setErrMsg(StringUtils.concat("[", rsp.getStatus(), "]", rsp.getMsg()));
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
        String encrypt = (String) params.get(JdpayParam.ENCRYPT.value());
        if (StringUtils.isNotEmpty(encrypt)) {
            params.putAll(parser.parse(JdpaySecurity.instance.decrypt(encrypt,
                    ((JdpayConfig) context.getConfig()).getMchKey())));
        }
        if (!JdpaySecurity.instance.verify((JdpayConfig) context.getConfig(), params)) {
            throw new IllegalChanelSignatureException();
        }
        TradingResult result = handle(context, params);
        if (null == result.getStatus())
            result.setStatus(TradingStatus.SUCCESS);
        return result.setData("ok");
    }

}