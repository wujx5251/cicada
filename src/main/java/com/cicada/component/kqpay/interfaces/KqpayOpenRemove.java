package com.cicada.component.kqpay.interfaces;

import com.cicada.component.kqpay.bean.KqPartObject;
import com.cicada.component.kqpay.enums.KqNodeType;
import com.cicada.component.kqpay.enums.KqpayAction;
import com.cicada.component.kqpay.enums.KqpayParam;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;

/**
 * 快钱开通解除绑卡
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.BIND_REMOVE, channel = Channel.KQPAY, product = ProductType.QUICK_PRE)
public class KqpayOpenRemove extends KqpayOpenQuery {

    @Override
    protected String getAction() {
        return KqpayAction.OPEN_REMOVE.value();
    }

    @Override
    protected String getNodeName() {
        return KqNodeType.PCI_DEL.value();
    }

    @Override
    protected KqPartObject getParam(TradingContext context) {
        KqPartObject param = super.getParam(context);
        param.put(KqpayParam.BANK_ID.value(), context.getCard().getOrgCode());
        param.put(KqpayParam.BIND_TYPE.value(), "0");
        return param;
    }

}