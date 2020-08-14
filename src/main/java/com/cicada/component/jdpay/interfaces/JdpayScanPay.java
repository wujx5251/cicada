package com.cicada.component.jdpay.interfaces;

import com.cicada.component.jdpay.bean.JdResponse;
import com.cicada.component.jdpay.emuns.JdpayTradeType;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.enums.TradingStatus;
import com.dotin.common.utils.Base64Utils;
import com.dotin.common.utils.QrcodeUtils;

/**
 * 京东扫码 支付
 *
 * @author: WUJXIAO
 * @create: 2019-01-26 10:58
 * @version: 1.0
 **/
@Component(value = ComponetType.SCAN_PAY, channel = Channel.JDPAY, product = ProductType.SCAN)
public class JdpayScanPay extends JdpayAppPay {

    @Override
    protected String getTradeType() {
        return JdpayTradeType.QR.value();
    }


    @Override
    public TradingResult render(TradingContext context, TradingResult result) {
        String codeImage = Base64Utils.encode(QrcodeUtils.write2Byte(((JdResponse) result.getData()).getQrCode(), 100));

        return result.setStatus(TradingStatus.PENDING).
                setAmount(context.getOrder().getAmount()).
                setData(codeImage);
    }
}