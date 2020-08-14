package com.cicada.core.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.cicada.component.kqpay.bean.KqpayConfig;
import com.cicada.component.unionpay.bean.UnionpayConfig;
import com.cicada.component.wxpay.bean.WxpayConfig;
import com.dotin.common.enums.CertTypeEnum;
import com.dotin.common.utils.Base64Utils;
import com.dotin.common.utils.crypto.Cert;
import com.dotin.common.utils.crypto.X509CertUtils;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 证书序列化
 *
 * @author: WUJXIAO
 * @create: 2018-12-17 09:32
 * @version: 1.0
 */
public class CertSerializer implements ObjectSerializer, ObjectDeserializer {

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName,
                      Type fieldType, int features) throws IOException {
        serializer.write(Base64Utils.encode(((Cert) object).getEncoded()));
    }

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        String value = parser.parseObject(String.class);
        if (Cert.class.isAssignableFrom((Class) type)) {
            return (T) getCert(parser.getContext().object, fieldName, value);
        }
        return null;
    }

    @Override
    public int getFastMatchToken() {
        //return JSONToken.LITERAL_INT;
        return 0;
    }

    private Cert getCert(Object obj, Object fieldName, String val) {
        if (obj instanceof WxpayConfig && "apiCert".equals(fieldName)) {
            return X509CertUtils.getCert(val, CertTypeEnum.PKCS12, ((WxpayConfig) obj).getMchId());
        } else if (obj instanceof UnionpayConfig && "mchCert".equals(fieldName)) {
            return X509CertUtils.getCert(val, CertTypeEnum.PKCS12, ((UnionpayConfig) obj).getMchCertPwd());
        } else if (obj instanceof KqpayConfig && "mchCert".equals(fieldName)) {
            return X509CertUtils.getCert(val, CertTypeEnum.PKCS12, ((KqpayConfig) obj).getMchCertPwd());
        }
        return X509CertUtils.getCert(val, CertTypeEnum.CER);
    }
}