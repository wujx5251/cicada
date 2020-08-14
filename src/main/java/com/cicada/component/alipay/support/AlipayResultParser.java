package com.cicada.component.alipay.support;

import com.cicada.component.alipay.enums.AlipayParam;
import com.dotin.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝返回查询字符串解析
 *
 * @author WUJXIAO
 * @version 1.0
 * @create 2018-12-13 19:05
 */
public class AlipayResultParser {

    public final static AlipayResultParser instance = new AlipayResultParser();

    /**
     * 将形如key=value&key=value的字符串转换为相应的Map对象
     *
     * @param data
     * @return
     */
    public Map<String, Object> parse(String data) {
        if (StringUtils.isEmpty(data))
            return null;
        int len = data.length();
        Map<String, Object> map = new HashMap<String, Object>(2);
        // 加签源串起点
        String rootNode = AlipayParam.RESPONSE.value();
        String signNode = AlipayParam.SIGN.value();
        int indexOfRootNode = data.indexOf(rootNode);
        //  第一个字母+长度+引号和分号
        int start = indexOfRootNode + rootNode.length() + 2;
        int indexOfSign = data.indexOf("\"" + signNode + "\"");
        if (indexOfSign < 0) {//sign字段不存在
            indexOfSign = len;
        }
        // 签名前-逗号
        int end = indexOfSign - 1;
        map.put(rootNode, data.substring(start, end));
        if (indexOfSign < len) {//sign字段不存在
            start = indexOfSign + signNode.length() + 4;
            end = data.lastIndexOf("\"}");
            map.put(AlipayParam.SIGN.value(), data.substring(start, end));
        }
        return map;
    }


}