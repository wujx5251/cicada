package com.cicada.core.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.cicada.core.enums.*;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 枚举序列化
 *
 * @author: WUJXIAO
 * @create: 2018-12-17 09:32
 * @version: 1.0
 */
public class EunmSerializer implements ObjectSerializer, ObjectDeserializer {

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName,
                      Type fieldType, int features) throws IOException {
        serializer.write(((Valuable) object).value());
    }


    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        String value = parser.parseObject(String.class);
        if (SmsType.class.isAssignableFrom((Class) type)) {
            return (T) SmsType.from(value);
        } else if (CardType.class.isAssignableFrom((Class) type)) {
            return (T) CardType.from(value);
        } else if (Currency.class.isAssignableFrom((Class) type)) {
            return (T) Currency.from(value, Currency.CNY);
        } else if (CertificationType.class.isAssignableFrom((Class) type)) {
            return (T) CertificationType.from(value);
        }
        return null;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}