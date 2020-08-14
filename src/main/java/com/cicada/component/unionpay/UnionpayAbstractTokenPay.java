package com.cicada.component.unionpay;

import com.cicada.component.unionpay.bean.UnionpayConfig;
import com.cicada.component.unionpay.enums.UnionpayBizType;
import com.cicada.component.unionpay.enums.UnionpayParam;
import com.cicada.core.bean.TradingCard;
import com.cicada.core.bean.TradingContext;
import com.cicada.utils.PayUtils;
import com.cicada.utils.SecurityUtils;
import com.dotin.common.utils.Base64Utils;
import com.dotin.common.utils.StringUtils;
import com.dotin.common.utils.crypto.Cert;

import java.util.HashMap;
import java.util.Map;

/**
 * 银联 快捷支付（无跳转）
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public abstract class UnionpayAbstractTokenPay extends UnionpayAbstractReqPay {

    /**
     * 生成客户信息
     *
     * @return
     */
    @Override
    protected Map<String, Object> getParams(TradingContext context) {
        Map<String, Object> params = super.getParams(context);
        params.put(UnionpayParam.BIZ_TYPE.value(), UnionpayBizType.TOKEN_PAY);
        TradingCard card = context.getCard();
        UnionpayConfig config = context.getConfig();

        if (null != card) {//卡信息（绑卡、短信发送时必填）
            String info = getCustomerInfo(card,
                    null != context.getExtra() ? context.getExtra().getAuthCode() : null,
                    config.getSecureCert());

            if (StringUtils.isNotBlank(info)) {
                params.put(UnionpayParam.ENCRYPT_CERT_ID.value(), config.getSecureCert().getCertNo());
                params.put(UnionpayParam.CUSTOMER_INFO.value(), info);
            }
            info = card.getNo();
            if (config.isAccEnc()) {
                info = SecurityUtils.encryptData(config.getSecureCert().getPublicKey(), card.getNo());
                if (StringUtils.isNotBlank(info) && !params.containsKey(UnionpayParam.ENCRYPT_CERT_ID)) {
                    params.put(UnionpayParam.ENCRYPT_CERT_ID.value(), config.getSecureCert().getCertNo());
                }
            }
            params.put(UnionpayParam.ACC_NO.value(), info);                                                         //卡号信息
        }

        String temp = PayUtils.getQueryStr(getTokenInfo(context, config.getTrId()), false, false);                  //支付标记
        if (StringUtils.isNotBlank(temp)) {
            params.put(UnionpayParam.TOKEN_PAY_DATA.value(), StringUtils.concat("{", temp, "}"));
        }
        return params;
    }

    protected String getCustomerInfo(TradingCard card, String smsCode, Cert cert) {
        Map<String, Object> part = new HashMap<String, Object>(8);
        //加密信息
        part.put(UnionpayParam.PHONE_NO.value(), card.getMobileNo());                                               //手机号码
        part.put(UnionpayParam.CVV2.value(), card.getCvv());                                                        //卡背面的cvn2三位数字
        part.put(UnionpayParam.EXPRIRED.value(), card.getExpire());                                                 //有效期 年在前月在后
        String temp = PayUtils.getQueryStr(part, false, false);//获取敏感数据
        part.clear();
        if (StringUtils.isNotBlank(temp)) {
            part.put(UnionpayParam.ENCRYPT_INFO.value(), SecurityUtils.encryptData(cert.getPublicKey(), temp));                   //加密信息域
        }
        //组装客户信息
        part.put(UnionpayParam.CERTIF_TP.value(), card.getCertificationType());                                     //证件类型
        part.put(UnionpayParam.CERTIF_ID.value(), card.getCertificationNo());                                       //持卡人证件号码
        part.put(UnionpayParam.HOLDER_NAME.value(), card.getHolderName());                                          //持卡人姓名
        part.put(UnionpayParam.SMS_CODE.value(), smsCode);                                                          //短信验证码

        temp = PayUtils.getQueryStr(part, false, false);//获取客户信息
        if (StringUtils.isNotBlank(temp)) {
            return Base64Utils.encode(StringUtils.concat("{", temp, "}"));
        }
        return null;
    }

    protected Map<String, String> getTokenInfo(TradingContext context, String trId) {
        Map<String, String> part = new HashMap<String, String>(2);
        part.put(UnionpayParam.TOKEN.value(), getToken(context));
        part.put(UnionpayParam.TRID.value(), trId);
        return part;
    }

    protected String getToken(TradingContext context) {
        if (null != context.getCard()) {
            return context.getCard().getToken();
        }
        return null;
    }
}
