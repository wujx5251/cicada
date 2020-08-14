package com.cicada.component.unionpay.support;

import com.dotin.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 银联返回查询字符串解析
 *
 * @author WUJXIAO
 * @version 1.0
 * @create 2018-12-13 19:05
 */
public class UnionpayResultParser {

    public final static UnionpayResultParser instance = new UnionpayResultParser();

    /**
     * 将形如key=value&key=value的字符串转换为相应的Map对象
     *
     * @param data
     * @return
     */
    public Map<String, ?> parse(String data) {
        if (StringUtils.isEmpty(data))
            return null;

        if (data.startsWith("{") && data.endsWith("}")) {
            data = data.substring(1, data.length() - 1);
        }
        int len = data.length();
        StringBuilder temp = new StringBuilder();
        char curChar, openName = 0;
        String key = null;
        boolean isKey = true, isOpen = false;//值里有嵌套
        Map<String, String> map = null;
        if (len > 0) {
            map = new HashMap<String, String>(32);
            for (int i = 0; i < len; i++) {// 遍历整个带解析的字符串
                curChar = data.charAt(i);// 取当前字符
                if (isKey) {// 如果当前生成的是key
                    if (curChar == '=') {// 如果读取到=分隔符
                        key = temp.toString();
                        temp.setLength(0);
                        isKey = false;
                    } else {
                        temp.append(curChar);
                    }
                } else {// 如果当前生成的是value
                    if (isOpen) {
                        if (curChar == openName) {
                            isOpen = false;
                        }
                    } else {//如果没开启嵌套
                        if (curChar == '{') {//如果碰到，就开启嵌套
                            isOpen = true;
                            openName = '}';
                        }
                    }
                    if (curChar == '&' && !isOpen) {// 如果读取到&分割符,同时这个分割符不是值域，这时将map里添加
                        map.put(key, temp.toString());
                        temp.setLength(0);
                        isKey = true;
                    } else {
                        temp.append(curChar);
                    }
                }
            }
            map.put(key, temp.toString());
        }
        return map;
    }

}