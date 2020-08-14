package com.cicada.component.kqpay.interfaces;

import com.cicada.component.kqpay.KqpayAbstractReqPay;
import com.cicada.component.kqpay.bean.KqPartObject;
import com.cicada.component.kqpay.bean.KqResponse;
import com.cicada.component.kqpay.bean.KqpayConfig;
import com.cicada.component.kqpay.enums.*;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.TradingStatus;
import com.dotin.common.utils.ObjectUtils;
import com.dotin.common.utils.StringUtils;

/**
 * 快钱支付查询
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.QUERY, channel = Channel.KQPAY)
public class KqpayQuery extends KqpayAbstractReqPay {

    @Override
    protected String getAction() {
        return KqpayAction.QUERY_STATUS.value();
    }

    @Override
    protected String getNodeName() {
        return KqNodeType.QRY_TXN.value();
    }

    @Override
    public KqPartObject getParam(TradingContext context) {
        KqpayConfig config = context.getConfig();

        KqPartObject params = KqPartObject.instance().
                put(KqpayParam.MERC_ID.value(), config.getMchId()).
                put(KqpayParam.TERM_ID.value(), config.getTerminalId()).
                put(KqpayParam.TRADE_NO.value(), context.getTarget().getRecordId());

        switch (context.getTarget().getProductType()) {
            case QUICK:
                params.put(KqpayParam.TXN_TYPE.value(), KqTxnType.PUR);
                break;
            case AUTH:
                params.put(KqpayParam.TXN_TYPE.value(), KqTxnType.PRE);
                break;
            case AUTH_CONFIRM:
                params.put(KqpayParam.TXN_TYPE.value(), KqTxnType.CFM);
                break;
            case CANCEL:
                params.put(KqpayParam.TXN_TYPE.value(), KqTxnType.VTX);
                break;
            case REFUND:
                params.put(KqpayParam.TXN_TYPE.value(), KqTxnType.RFD);
                break;
        }
        return params;
    }

    @Override
    protected TradingResult render(TradingContext context, Object params) {
        TradingResult result = super.render(context, params);
        result.setRecordId(context.getTarget().getRecordId());
        result.setAmount(context.getTarget().getOrder().getAmount() +
                context.getTarget().getOrder().getRefundAmount());

        KqResponse rsp = (KqResponse) result.getData();

        if (TradingStatus.SUCCESS.equals(result.getStatus())) {
            result.setErrMsg(StringUtils.concat("[", rsp.getStatus(), "]", rsp.getErrMsg()));

            if (KqpayStatus.SUCCESS.value().equals(rsp.getStatus())) {
                result.setStatus(TradingStatus.SUCCESS);
            } else if (KqpayStatus.FAIL.value().equals(rsp.getStatus())) {
                result.setStatus(TradingStatus.FAIL);
            } else if (ObjectUtils.contains(rsp.getCode(), KqResponse.PENDING)) {
                result.setStatus(TradingStatus.PENDING);
            }
        } else {
            result.setStatus(context.getTarget().getResult().getStatus());
        }
        return result;
    }

}