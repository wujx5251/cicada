package com.cicada.component.kqpay;

import com.cicada.component.kqpay.bean.KqResponse;
import com.cicada.component.kqpay.bean.KqpayConfig;
import com.cicada.component.kqpay.support.KqErrDict;
import com.cicada.component.kqpay.support.KqpaySecurity;
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
 * 快钱异步通知
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public abstract class KqpayAbstractNotify extends AbstractPay {

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
        KqResponse rsp = KqResponse.from((Map<String, String>) params);

        TradingResult result = new TradingResult().
                setRetry(context.getConfig().isRetry()).
                setRecordId(context.getRecordId()).
                setReplyId(rsp.getReplyId()).
                setData(rsp);

        if ((null != rsp.getCode() || null != rsp.getErrCode()) && !KqResponse.SUCCESS.equals(rsp.getCode())) {
            String msg = rsp.getMsg();
            if (StringUtils.isEmpty(msg)) {
                msg = KqErrDict.getErrMsg(rsp.getCode());
            }
            if (ObjectUtils.contains(rsp.getCode(), KqResponse.PENDING)) {
                return result.setStatus(TradingStatus.PENDING).setErrMsg(StringUtils.concat("[", rsp.getCode(), "]", msg));
            } else if (StringUtils.isNotEmpty(rsp.getErrCode())) {
                return result.setStatus(TradingStatus.FAIL).setErrMsg(StringUtils.concat("[", rsp.getErrCode(), "]", rsp.getErrMsg()));
            } else {
                return result.setStatus(TradingStatus.FAIL).setErrMsg(StringUtils.concat("[", rsp.getCode(), "]", msg));
            }
        }
        return result;
    }

    @Override
    protected RequestExecutor getRequestExecutor(TradingContext context) {
        return null;
    }

    @Override
    public TradingResult handleNotify(TradingContext context) {
        String tr3Xml = context.getMessage().getAgentRequestData();
        if (!KqpaySecurity.instance.verify((KqpayConfig) context.getConfig(), tr3Xml)) {
            throw new IllegalChanelSignatureException();
        }
        Map<String, Object> params = XmlUtils.toMap(tr3Xml, xmlConfig);
        TradingResult result = handle(context, params);
        if (null == result.getStatus()) {
            result.setStatus(TradingStatus.SUCCESS);
        }
        StringBuffer tr4XML = new StringBuffer();
        tr4XML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><MasMessage xmlns=\"http://www.99bill.com/mas_cnp_merchant_interface\">");
        tr4XML.append("<version>1.0</version><TxnMsgContent>");
        tr4XML.append("<txnType>").append(params.get("txnType")).append("</txnType>");
        tr4XML.append("<interactiveStatus>TR4</interactiveStatus>");
        tr4XML.append("<merchantId>").append(params.get("merchantId")).append("</merchantId>");
        tr4XML.append("<terminalId>").append(params.get("terminalId")).append("</terminalId>");
        tr4XML.append("<refNumber>").append(params.get("refNumber")).append("</refNumber>");
        tr4XML.append("</TxnMsgContent></MasMessage>");
        return result.setData(tr4XML.toString());
    }

    private static final XmlUtils.Config xmlConfig = XmlUtils.Config.build().setEditor(new XmlUtils.ValueEditor() {
        @Override
        protected <T> T getValue(Object parent, Object obj, Object fieldName) {
            if (parent instanceof Map && obj instanceof Map) {
                ((Map) parent).putAll((Map) obj);
                return null;
            }
            return (T) obj;
        }
    });


}