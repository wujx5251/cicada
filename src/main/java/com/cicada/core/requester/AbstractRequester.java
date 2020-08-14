package com.cicada.core.requester;


import com.cicada.Config;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.Valuable;
import com.cicada.core.exception.internal.GatewayConnectionException;
import com.cicada.core.exception.internal.GatewayRequestException;
import com.cicada.core.exception.internal.GatewayTimeOutException;
import com.dotin.common.exception.ReadTimeoutException;
import com.dotin.common.exception.SocketConnectException;
import com.dotin.common.net.http.*;
import com.dotin.common.utils.NumberUtils;
import com.dotin.common.utils.StringUtils;
import com.dotin.common.utils.XmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.Security;
import java.util.Map;

/**
 * 请求处理器
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public abstract class AbstractRequester<T> implements RequestExecutor<T> {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractRequester.class);

    protected final HttpClient client = new HttpUrlClient();
    protected final XmlUtils.Config xmlConfig = XmlUtils.Config.build().setHasVer(false);

    protected AbstractRequester() {
        xmlConfig.setEditor(valueEditor);
    }

    protected Response doRequest(String getaway, Header... headers) {
        return doRequest(getaway, null, null, null, null, null, null, headers);
    }

    protected Response doRequest(String getaway, byte[] param, Header... headers) {
        return doRequest(getaway, param, null, null, null, null, null, headers);
    }

    protected Response doRequest(String getaway, byte[] param,
                                 KeyStore ks, String ksPwd, Header... headers) {
        return doRequest(getaway, param, null, null, ks, ksPwd, null, headers);
    }

    protected Response doRequest(String getaway, byte[] param,
                                 HttpClient.ContentType ctype,
                                 KeyStore ks, String ksPwd, Header... headers) {
        return doRequest(getaway, param, ctype, null, ks, ksPwd, null, headers);
    }

    protected Response doRequest(String getaway, byte[] param,
                                 HttpClient.ContentType ctype, Header... headers) {
        return doRequest(getaway, param, ctype, null, null, null, null, headers);
    }

    protected Response doRequest(String getaway, byte[] param,
                                 HttpClient.MethodType method, Header... headers) {
        return doRequest(getaway, param, null, null, null, null, method, headers);
    }

    protected Response doRequest(String getaway, byte[] param,
                                 HttpClient.ContentType ctype,
                                 Charset charset,
                                 KeyStore ks, String ksPwd,
                                 HttpClient.MethodType method, Header... headers) {

        Response rsp;
        try {
            rsp = client.doRequest(getaway, param, RequestConfig.custom().
                    setConnectTimeout(3000).
                    setReadTimeout(Config.getReqTimeout()).
                    setHeaders(headers).
                    setContenttype(ctype).
                    setCharset(charset).
                    setKeyStore(ks).
                    setMethod(method).
                    setKeyStorePwd(ksPwd).build());
        } catch (SocketConnectException e) {
            throw new GatewayConnectionException(StringUtils.concat("url[", getaway, "] connect timeout"), e);
        } catch (ReadTimeoutException e) {
            throw new GatewayTimeOutException(StringUtils.concat("url[", getaway, "] read timeout"), e);
        }


        if (!NumberUtils.isEquals(200, rsp.getCode())) {
            throw new GatewayRequestException(StringUtils.concat("url[", getaway, "] request fail, code[", rsp.getCode(), "], error msg:", rsp.getBody()));
        }

        return rsp;
    }

    @Override
    public boolean verify(TradingContext context, T rsp) {
        return true;
    }

    private static final XmlUtils.ValueEditor valueEditor = new XmlUtils.ValueEditor() {
        @Override
        protected <T> T getValue(Object parent, Object obj, Object fieldName) {
            if (parent == null) {
                if (obj instanceof Valuable)
                    return (T) ((Valuable) obj).value();
            } else if (parent instanceof Map && obj instanceof Map) {
                ((Map) parent).putAll((Map) obj);
                return null;
            }
            return (T) obj;
        }
    };

    static {
        //清除安全设置
        Security.setProperty("jdk.certpath.disabledAlgorithms", "");
    }

}
