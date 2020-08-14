package com.cicada.core.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.dotin.common.utils.Base64Utils;
import com.dotin.common.utils.crypto.RsaUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 密钥序列化
 *
 * @author: WUJXIAO
 * @create: 2018-12-17 09:32
 * @version: 1.0
 */
public class KeySerializer implements ObjectSerializer, ObjectDeserializer {

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName,
                      Type fieldType, int features) throws IOException {
        serializer.write(Base64Utils.encode(((Key) object).getEncoded()));
    }


    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        String value = parser.parseObject(String.class);
        if (PublicKey.class.isAssignableFrom((Class) type)) {
            return (T) RsaUtils.getPublicKey(value);
        } else if (PrivateKey.class.isAssignableFrom((Class) type)) {
            return (T) RsaUtils.getPrivateKey(value);
        }
        return null;
    }

    @Override
    public int getFastMatchToken() {
        //return JSONToken.LITERAL_INT;
        return 0;
    }
}