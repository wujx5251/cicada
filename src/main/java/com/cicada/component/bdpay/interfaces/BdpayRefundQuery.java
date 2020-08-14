package com.cicada.component.bdpay.interfaces;

import com.cicada.component.bdpay.emuns.BdpayAction;
import com.cicada.core.annotation.Component;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;

/**
 * 百度钱包退货查询
 *
 * @author: WUJXIAO
 * @create: 2019-01-26 10:58
 * @version: 1.0
 **/
@Component(value = ComponetType.REFUND_QUERY, channel = Channel.BDPAY)
public class BdpayRefundQuery extends BdpayQuery {

    @Override
    protected String getAction() {
        return BdpayAction.REFUND_QUERY.value();
    }

}