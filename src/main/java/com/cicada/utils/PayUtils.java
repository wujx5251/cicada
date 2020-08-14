package com.cicada.utils;

import com.cicada.core.enums.Valuable;
import com.dotin.common.base.Charsets;
import com.dotin.common.utils.StringUtils;
import com.dotin.common.utils.web.URLCodec;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 工具类
 * Created by jinxiao.wu
 */
public final class PayUtils {

    private static final Logger logger = LoggerFactory.getLogger(PayUtils.class);

    private final static BigDecimal ONE_HUNDRED = new BigDecimal("100");

    private PayUtils() {
    }

    public static String getQueryStr(Map<String, ?> params) {
        return getQueryStr(params, Charsets.UTF_8, false, true);
    }

    public static String getQueryStr(Map<String, ?> params, Charset charset) {
        return getQueryStr(params, charset, false, true);
    }

    public static String getQueryStr(Map<String, ?> params, Charset charset, boolean filter) {
        return getQueryStr(params, charset, filter, false, true);
    }

    public static String getQueryStr(Map<String, ?> params, boolean encode) {
        return getQueryStr(params, Charsets.UTF_8, encode, true);
    }

    public static String getQueryStr(Map<String, ?> params, boolean encode, boolean sort) {
        return getQueryStr(params, Charsets.UTF_8, encode, sort);
    }

    public static String getQueryStr(Map<String, ?> params, boolean encode, Charset charset) {
        return getQueryStr(params, charset, encode, false);
    }

    public static String getQueryStr(Map<String, ?> params, Charset charset, boolean encode, boolean sort) {
        return getQueryStr(params, charset, true, encode, sort);
    }

    public static String getQueryStr(Map<String, ?> params, Charset charset, boolean filter, boolean encode, boolean sort) {
        if (null == params || params.isEmpty())
            return null;
        List<String> keys = new ArrayList<String>(params.keySet());
        if (sort) {
            Collections.sort(keys);
        }
        boolean first = true;
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object v = params.get(key);
            String value = StringUtils.parse(v instanceof Valuable ? ((Valuable) v).value() : v);
            if (StringUtils.isEmpty(value)) {
                //过滤
                if (filter) {
                    continue;
                }
                value = "";
            } else if (encode) {
                value = URLCodec.encode(value, charset.name());
            }
            if (!first) {
                //拼接时，不包括最后一个&字符
                buff.append("&");
            } else {
                first = false;
            }
            buff.append(key).append("=").append(value);
        }
        return buff.toString();
    }

    public static Map<String, Object> parseParams(HttpServletRequest request) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Set<Map.Entry<String, String[]>> params = request.getParameterMap().entrySet();
        for (Map.Entry<String, String[]> entry : params) {
            if (null != entry.getValue()) {//过滤空参数
                if (entry.getValue().length == 1) {
                    paramMap.put(entry.getKey(), entry.getValue()[0]);
                } else if (entry.getValue().length > 1) {
                    paramMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return paramMap;
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {

        }
    }

    /**
     * 金额单位转成分:分-->元，除以100
     *
     * @param money
     * @return String
     */
    public static String getCentMoney(Integer money) {
        if (null == money) return null;
        BigDecimal decimalMoney = new BigDecimal(money);
        BigDecimal result = decimalMoney.divide(ONE_HUNDRED);
        return result.toString();
    }

    /**
     * 获取银行卡端卡号 前六后四
     *
     * @param no 卡号
     * @return
     */
    public static String getShortCardNo(String no) {
        return getShortCardNo(no, true);
    }

    /**
     * 获取银行卡端卡号
     *
     * @param no     卡号
     * @param hidden 是否隐藏中间部分 false使用*代替
     * @return
     */
    public static String getShortCardNo(String no, boolean hidden) {
        if (StringUtils.isEmpty(no)) {
            return null;
        }
        String header = StringUtils.left(no, 6);
        String end = StringUtils.right(no, 4);
        StringBuilder builder = new StringBuilder(header);
        if (!hidden) {
            for (int i = 0, len = no.length() - 10; i < len; i++) {
                builder.append("*");
            }
        }
        builder.append(end);
        return builder.toString();
    }

    public static String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成get自提交 html内容
     *
     * @param reqUrl action
     * @return
     */
    public static String generateHtml(String reqUrl) {
        return generateHtml(reqUrl, null, "GET");
    }

    /**
     * 生成get自提交 html内容
     *
     * @param reqUrl action
     * @return
     */
    public static String generateHtml(String reqUrl, String method) {
        return generateHtml(reqUrl, null, method);
    }

    /**
     * 生成post自提交 html内容
     *
     * @param reqUrl  action
     * @param hiddens 参数
     * @return
     */
    public static String generateHtml(String reqUrl, Map<String, String> hiddens) {
        return generateHtml(reqUrl, hiddens, "POST");
    }

    /**
     * 生成自提交 html内容
     *
     * @param reqUrl  action
     * @param hiddens 参数
     * @param method  提交方式
     * @return
     */
    public static String generateHtml(String reqUrl, Map<String, ?> hiddens, String method) {
        return generateHtml(reqUrl, hiddens, method, Charsets.UTF_8);
    }

    /**
     * 生成自提交 html内容
     *
     * @param reqUrl  action
     * @param hiddens 参数
     * @param method  提交方式
     * @return
     */
    public static String generateHtml(String reqUrl, Map<String, ?> hiddens, String method, Charset charset) {
        StringBuilder sf = new StringBuilder();
        sf.append("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"");
        sf.append(charset);
        sf.append("\"/>");
        sf.append("<title>支付跳转中…</title></head><body onload=\"javascript:document.webPayForm.submit()\">");
        sf.append("<form name=\"webPayForm\" action=\"");
        sf.append(reqUrl);
        sf.append("\" method=\"");
        sf.append(method);
        sf.append("\">");

        if (null != hiddens && 0 != hiddens.size()) {
            Object val;
            for (Map.Entry<String, ?> entry : hiddens.entrySet()) {
                val = entry.getValue();
                if (null == val) continue;
                if (val instanceof Valuable) val = ((Valuable) val).value();

                sf.append("<input type=\"hidden\" name=\"" + entry.getKey());
                sf.append("\" value=\"");
                if (val instanceof String) {
                    val = StringEscapeUtils.escapeXml11((String) val);
                    sf.append(val);
                } else {
                    sf.append(val);
                }

                sf.append("\"/>");
            }
        }
        sf.append("</form></body></html>");
        return sf.toString();
    }
}