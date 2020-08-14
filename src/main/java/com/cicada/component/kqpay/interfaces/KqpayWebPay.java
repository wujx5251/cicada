package com.cicada.component.kqpay.interfaces;

import com.cicada.component.kqpay.KqpayAbstractPay;
import com.cicada.component.kqpay.bean.KqpayConfig;
import com.cicada.component.kqpay.enums.KqPayType;
import com.cicada.component.kqpay.enums.KqpayAction;
import com.cicada.component.kqpay.enums.KqpayParam;
import com.cicada.component.kqpay.support.KqpaySecurity;
import com.cicada.core.annotation.Component;
import com.cicada.core.bean.TradingCard;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.*;
import com.cicada.utils.PayUtils;
import com.cicada.utils.RequestUrlUtils;
import com.dotin.common.utils.DateUtils;
import com.dotin.common.utils.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 快钱Web 支付
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
@Component(value = ComponetType.WEB_PAY, channel = Channel.KQPAY, product = ProductType.WEB)
public class KqpayWebPay extends KqpayAbstractPay {

    @Override
    protected String getAction() {
        return KqpayAction.WEB_PAY.value();
    }

    @Override
    public final Map<String, ?> build(TradingContext context) {
        KqpayConfig config = context.getConfig();
        Map<String, Object> params = new LinkedHashMap<String, Object>(25);
        params.put(KqpayParam.CHARSET.value(), config.getCharset());
        params.put(KqpayParam.WEB_RETURN_URL.value(), RequestUrlUtils.getReturnUrl(context));
        params.put(KqpayParam.WEB_NOTIFY_URL.value(), RequestUrlUtils.getNotifyUrl(context));
        params.put(KqpayParam.VERSION.value(), config.getVersion());
        params.put(KqpayParam.LANGUAGE.value(), config.getLanguage());
        params.put(KqpayParam.SIGN_TYPE.value(), config.getSignType());

        params.put(KqpayParam.MER_ACC_ID.value(), config.getMchId());//商户ID
        params.put(KqpayParam.ORDER_ID.value(), context.getRecordId());//订单ID
        params.put(KqpayParam.ORDER_AMOUNT.value(), context.getOrder().getAmount());//订单金额,单位分
        params.put(KqpayParam.ORDER_TIME.value(), DateUtils.format(context.getCreateTime(),
                DateUtils.DATE_TIME_SHORT_FORMAT));//订单提交时间，格式：yyyyMMddHHmmss
        params.put(KqpayParam.PRODUCT_NAME.value(), context.getOrder().getSubject());//产品名称
        params.put(KqpayParam.PRODUCT_DESC.value(), context.getOrder().getBody());
        params.putAll(getParams(context));

        String sign = doSign(config, params);
        params.put(KqpayParam.SIGN.value(), sign);//签名
        return params;
    }

    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = new LinkedHashMap<String, Object>(6);
        KqpayConfig config = context.getConfig();
        params.put(KqpayParam.PAY_TYPE.value(), KqPayType.QUICK);//支付方式
        TradingCard card = context.getCard();
        if (null != card && StringUtils.isNotEmpty(card.getOrgCode())) {
            if (CardType.CREDIT.equals(card.getType())) {
                params.put(KqpayParam.PAY_TYPE.value(), KqPayType.WEB_CREDIT);
            } else if (CardType.DEBIT.equals(card.getType())) {
                params.put(KqpayParam.PAY_TYPE.value(), KqPayType.WEB_DEBIT);
            } else {
                params.put(KqpayParam.PAY_TYPE.value(), KqPayType.WEB);
            }

            params.put(KqpayParam.BANK_ID.value(), context.getCard().getOrgCode());//银行ID
        }
        params.put(KqpayParam.REDO_FLAG.value(), "0");//1代表只能提交一次，0代表在支付不成功情况下可以再提交
        //params.put(KqpayParam.SUBMIT_TYPE.value(), "00");//00:代表前台提交01：代表后台提交
        params.put(KqpayParam.TIME_OUT.value(), config.getTimeout());
        return params;
    }

    @Override
    public TradingResult render(TradingContext context, Object params) {
        KqpayConfig config = context.getConfig();
        String data = PayUtils.generateHtml(config.getGatewayUrl() + getAction(), (Map<String, String>) params);
        context.getMessage().setPagePayData(data);
        String url = RequestUrlUtils.getPayUrl(context);

        return new TradingResult(TradingStatus.PENDING).
                setRecordId(context.getRecordId()).
                setAmount(context.getOrder().getAmount()).
                setData(url);
    }

    private String doSign(KqpayConfig config, Map<String, ?> params) {
        return KqpaySecurity.instance.sign(config, params);
    }

}