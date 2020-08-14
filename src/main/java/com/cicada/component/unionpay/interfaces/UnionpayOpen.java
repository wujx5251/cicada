package com.cicada.component.unionpay.interfaces;

import com.cicada.component.unionpay.UnionpayAbstractTokenPay;
import com.cicada.component.unionpay.bean.UnResponse;
import com.cicada.component.unionpay.enums.UnionpayParam;
import com.cicada.component.unionpay.enums.UnionpaySubTxnType;
import com.cicada.component.unionpay.enums.UnionpayTxnType;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.storage.CardBindService;
import com.cicada.storage.bean.CardBind;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 银联在线支付开通（绑卡）
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.CARD_BIND, channel = Channel.UNIONPAY, product = ProductType.QUICK_PRE)
public class UnionpayOpen extends UnionpayAbstractTokenPay {

    @Autowired
    private CardBindService bindService;

    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        context.setCard(context.getTarget().getCard());
        Map<String, Object> params = super.getParams(context);
        params.put(UnionpayParam.TXN_TYPE.value(), UnionpayTxnType.OPEN_CARD);
        params.put(UnionpayParam.TXN_SUB_TYPE.value(), UnionpaySubTxnType.DEFAULT);
        return params;
    }

    @Override
    protected Map<String, String> getTokenInfo(TradingContext context, String trId) {
        Map<String, String> part = super.getTokenInfo(context, trId);
        part.remove(UnionpayParam.TOKEN.value());//绑卡交易不允许使用标记
        part.put(UnionpayParam.TOKEN_TYPE.value(), "01");//绑卡
        return part;
    }

    @Override
    protected TradingResult render(TradingContext context, TradingResult result) {
        result = super.render(context, result);
        context.getCard().setToken(((UnResponse) result.getData()).getToken());
        CardBind bind = bindService.bind(context);
        return result.setData(bind.getBindId());
    }
}