package com.cicada.component.kqpay.interfaces;

import com.cicada.component.kqpay.bean.KqPartObject;
import com.cicada.component.kqpay.enums.KqNodeType;
import com.cicada.component.kqpay.enums.KqpayAction;
import com.cicada.component.kqpay.enums.KqpayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.storage.CardBindService;
import com.cicada.storage.bean.CardBind;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 快钱开通绑卡确认
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.CARD_BIND, channel = Channel.KQPAY, product = ProductType.QUICK_PRE)
public class KqpayOpenConfirm extends KqpayOpen {

    @Autowired
    private CardBindService bindService;

    @Override
    protected String getAction() {
        return KqpayAction.OPEN_CONFIRM.value();
    }

    @Override
    protected String getNodeName() {
        return KqNodeType.IND_AUTH_VERIFY.value();
    }

    @Override
    protected KqPartObject getParam(TradingContext context) {
        context.setCard(context.getTarget().getCard());
        KqPartObject param = super.getParam(context);
        param.put(KqpayParam.TRADE_NO.value(), context.getTarget().getRecordId());
        param.put(KqpayParam.VALID_CODE.value(), context.getExtra().getAuthCode());
        param.put(KqpayParam.TOKEN.value(), context.getTarget().getExtra().getOpenId());
        param.remove(KqpayParam.SHORT_CARD_NO.value());
        return param;
    }

    @Override
    protected String getToken(TradingContext context) {
        return context.getTarget().getRecordId();
    }

    @Override
    protected TradingResult render(TradingContext context, TradingResult result) {
        result = super.render(context, result);
        context.getCard().setToken(getToken(context));
        CardBind bind = bindService.bind(context);
        return result.setData(bind.getBindId());
    }

}