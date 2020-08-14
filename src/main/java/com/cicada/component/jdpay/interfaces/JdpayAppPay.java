package com.cicada.component.jdpay.interfaces;

import com.cicada.component.jdpay.JdpayAbstractPay;
import com.cicada.component.jdpay.bean.JdResponse;
import com.cicada.component.jdpay.bean.JdpayConfig;
import com.cicada.component.jdpay.emuns.JdpayAction;
import com.cicada.component.jdpay.emuns.JdpayParam;
import com.cicada.component.jdpay.emuns.JdpayTradeType;
import com.cicada.component.jdpay.support.JdpaySecurity;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.enums.TradingStatus;
import com.dotin.common.utils.XmlUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 京东app 支付
 *
 * @author: WUJXIAO
 * @create: 2019-01-26 10:58
 * @version: 1.0
 **/
@Component(value = ComponetType.APP_PAY, channel = Channel.JDPAY, product = ProductType.APP)
public class JdpayAppPay extends JdpayAbstractPay {

    protected final static String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    @Override
    protected String getAction() {
        return JdpayAction.CREATE_ORDER.value();
    }

    @Override
    protected String getTradeType() {
        return JdpayTradeType.GEN.value();
    }

    @Override
    public TradingResult render(TradingContext context, TradingResult result) {
        super.render(context, result);
        return result.setStatus(TradingStatus.PENDING).
                setData(((JdResponse) result.getData()).getReplyId());
    }


    @Override
    protected String sign(JdpayConfig config, Map<String, Object> params) {
        String data = XmlUtils.toXml(params, JdpayParam.XML_ROOT.value());
        data = XML_HEAD + data;
        return JdpaySecurity.instance.signXml(config, data);
    }

    @Override
    protected Map<String, Object> encrypt(JdpayConfig config, Map<String, Object> params) {
        Map<String, Object> ps = new HashMap<String, Object>(3);
        ps.put(JdpayParam.VERSION.value(), params.get(JdpayParam.VERSION.value()));
        ps.put(JdpayParam.MCH_ID.value(), params.get(JdpayParam.MCH_ID.value()));
        String data = XmlUtils.toXml(params, JdpayParam.XML_ROOT.value());
        data = XML_HEAD + data;
        ps.put(JdpayParam.ENCRYPT.value(), JdpaySecurity.instance.encryptXml(config, data));
        return ps;
    }

}