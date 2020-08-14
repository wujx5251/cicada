package com.cicada.component.jdpay.support;

import com.cicada.component.jdpay.emuns.JdpayParam;
import com.dotin.common.utils.StringUtils;
import com.dotin.common.utils.XmlUtils;

import java.util.Map;

/**
 * 京东返回查询字符串解析
 *
 * @author WUJXIAO
 * @version 1.0
 * @create 2018-12-13 19:05
 */
public class JdpayResultParser {

    public final static JdpayResultParser instance = new JdpayResultParser();

    /**
     * 将形如key=value&key=value的字符串转换为相应的Map对象
     *
     * @param data
     * @return
     */
    public Map<String, Object> parse(String data) {
        if (StringUtils.isEmpty(data))
            return null;
        Map<String, Object> map = XmlUtils.toMap(data);
        // 加签源串起点
        map.put(JdpayParam.RESPONSE.value(), delXmlElm(data, JdpayParam.SIGN.value()));
        return map;
    }


    private String delXmlElm(String xml, String elmName) {
        String elStart = "<" + elmName + ">";
        String elEnd = "</" + elmName + ">";
        if ((xml.contains(elStart)) && (xml.contains(elEnd))) {
            int i1 = xml.indexOf(elStart);
            int i2 = xml.lastIndexOf(elEnd);
            String start = xml.substring(0, i1);
            int length = elEnd.length();
            String end = xml.substring(i2 + length, xml.length());
            xml = start + end;
        }
        return xml;
    }

}