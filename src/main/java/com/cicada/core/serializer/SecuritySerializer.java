package com.cicada.core.serializer;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.dotin.common.utils.StringUtils;


/**
 * 参数序列化安全工具
 * Created by jinxiao.wu
 */
public class SecuritySerializer implements ValueFilter {

    private static String[] KEYS = {"cardNo", "mobileNo", "holderName", "certificationNo", "cvv2", "expireMonth", "expireYear"};
    private static int[][] HIDDEN = {{6, 4}, {3, 4}, {1, 0}, {6, 4}, null, null, null};

    private static SecuritySerializer serializer = new SecuritySerializer();

    public static SecuritySerializer instance() {
        return serializer;
    }

    @Override
    public Object process(Object object, String name, Object value) {
        for (int i = 0, len = KEYS.length; i < len; i++) {
            if (name.equals(KEYS[i])) {
                if (null != value) {
                    return getValue(value, HIDDEN[i]);
                }
                break;
            }
        }
        return value;
    }

    private String getValue(Object value, int[] format) {
        String val = value.toString();
        int len = val.length();
        String header = null, end = null;
        if (null != format) {
            if (format[0] > 0)
                header = StringUtils.left(val, format[0]);
            if (format[1] > 0)
                end = StringUtils.right(val, format[1]);
            len -= (format[0] + format[1]);
        }
        StringBuilder builder = new StringBuilder();
        if (null != header)
            builder.append(header);
        for (int i = 0; i < len; i++) {
            builder.append("*");
        }
        if (null != end)
            builder.append(end);
        return builder.toString();
    }


}