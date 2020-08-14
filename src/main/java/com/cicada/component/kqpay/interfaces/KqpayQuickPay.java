package com.cicada.component.kqpay.interfaces;

import com.cicada.component.kqpay.KqpayAbstractReqPay;
import com.cicada.component.kqpay.bean.KqPartArray;
import com.cicada.component.kqpay.bean.KqPartObject;
import com.cicada.component.kqpay.enums.KqNodeType;
import com.cicada.component.kqpay.enums.KqTxnType;
import com.cicada.component.kqpay.enums.KqpayAction;
import com.cicada.component.kqpay.enums.KqpayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.utils.PayUtils;
import com.cicada.utils.RequestUrlUtils;
import com.dotin.common.utils.DateUtils;

/**
 * 快钱快捷 支付
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.QUICK_PAY, channel = Channel.KQPAY, product = ProductType.QUICK)
public class KqpayQuickPay extends KqpayAbstractReqPay {

    @Override
    protected String getAction() {
        return KqpayAction.CONSUME.value();
    }

    @Override
    protected String getNodeName() {
        return KqNodeType.TXN_MSG.value();
    }

    @Override
    protected KqPartObject getParam(TradingContext context) {
        KqPartObject param = super.getParam(context);
        param.put(KqpayParam.TXN_TYPE.value(), KqTxnType.PUR);
        param.put(KqpayParam.INTER_STATUS.value(), "TR1");
        param.put(KqpayParam.AMOUNT.value(), PayUtils.getCentMoney(context.getOrder().getAmount()));
        param.put(KqpayParam.TRADE_TIME.value(), DateUtils.format(context.getCreateTime(),
                DateUtils.DATE_TIME_SHORT_FORMAT));
        param.put(KqpayParam.SP_FLAG.value(), "QPay02");
        param.put(KqpayParam.NOTIFY_URL.value(), RequestUrlUtils.getNotifyUrl(context));
        param.put(KqpayParam.EXT_MAP.value(), KqPartObject.instance().
                        put(KqpayParam.EXT_DATA.value(), KqPartArray.instance().
                                        add(KqPartObject.instance().
                                                put(KqpayParam.EXT_DATA_KEY.value(), "payBatch").
                                                put(KqpayParam.EXT_DATA_VALUE.value(), 2))
                        )
        );
        smsInfo(param, context);
        param.remove(KqpayParam.SHORT_ACC_NO.value());
        return param;
    }

    protected void smsInfo(KqPartObject param, TradingContext context) {
        TradingContext target = context.getTarget();
        if (null != target) {//短信二次确认支付
            param.put(KqpayParam.TRADE_NO.value(), target.getRecordId());
            param.put(KqpayParam.SP_FLAG.value(), "QuickPay");
            param.put(KqpayParam.EXT_MAP.value(), KqPartObject.instance().
                            put(KqpayParam.EXT_DATA.value(), KqPartArray.instance().
                                            add(KqPartObject.instance().
                                                    put(KqpayParam.EXT_DATA_KEY.value(), KqpayParam.VALID_CODE).
                                                    put(KqpayParam.EXT_DATA_VALUE.value(), null == context.getExtra() ? null : context.getExtra().getAuthCode())).
                                            add(KqPartObject.instance().
                                                    put(KqpayParam.EXT_DATA_KEY.value(), KqpayParam.TOKEN).
                                                    put(KqpayParam.EXT_DATA_VALUE.value(), null == target.getExtra() ? null : target.getExtra().getOpenId())).
                                            add(KqPartObject.instance().
                                                    put(KqpayParam.EXT_DATA_KEY.value(), "payBatch").
                                                    put(KqpayParam.EXT_DATA_VALUE.value(), 2))
                            )
            );
        }
    }

    @Override
    protected String getShortCardNo(TradingContext context) {
        if (null != context.getTarget()) {
            return super.getShortCardNo(context.getTarget());
        } else {
            return super.getShortCardNo(context);
        }
    }

    @Override
    protected String getToken(TradingContext context) {
        if (null != context.getTarget()) {
            return super.getToken(context.getTarget());
        } else {
            return super.getToken(context);
        }
    }

}