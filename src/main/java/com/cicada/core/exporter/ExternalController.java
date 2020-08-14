package com.cicada.core.exporter;

import com.alibaba.fastjson.JSON;
import com.cicada.Constants;
import com.cicada.component.wxpay.bean.WxpayConfig;
import com.cicada.core.Pay;
import com.cicada.core.bean.Application;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.definition.ComponentDefinition;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ProductType;
import com.cicada.core.enums.TradingStatus;
import com.cicada.core.exception.business.ExpireException;
import com.cicada.core.exception.business.RecordNotFoundException;
import com.cicada.storage.bean.RecordDetail;
import com.cicada.utils.PayUtils;
import com.cicada.utils.RequestUrlUtils;
import com.dotin.common.base.Charsets;
import com.dotin.common.net.http.HttpClient;
import com.dotin.common.net.http.HttpUrlClient;
import com.dotin.common.net.http.Response;
import com.dotin.common.utils.DateUtils;
import com.dotin.common.utils.StreamUtils;
import com.dotin.common.utils.StringUtils;
import com.dotin.common.utils.web.CookieUtils;
import com.dotin.common.utils.web.URLCodec;
import com.dotin.spring.holder.SpringContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

/**
 * 外部请求访问控制器，处理web支付、回调、页面跳转
 *
 * @author WUJXIAO
 * @version 1.0
 * @create 2019-01-04 13:45
 */
@Controller
public class ExternalController extends AbstractNotifyExporter {

    private final static HttpClient httpclient = new HttpUrlClient();


    @RequestMapping(value = "/pay/{channel}/{product}/{recordId}", method = RequestMethod.GET)
    public void loadPay(@PathVariable String channel,
                        @PathVariable String product,
                        @PathVariable String recordId,
                        HttpServletResponse response) throws IOException {
        String payInfo;
        boolean hasErr = false;
        try {
            Channel.from(channel);
            ProductType.fromByValue(product);
            RecordDetail info = detailDao.loadPayInfo(recordId);
            if (null == info) {
                throw new RecordNotFoundException(recordId);
            } else if (DateUtils.compare(DateUtils.now(), info.getExpireTime()) > 0) {
                throw new ExpireException(recordId);
            }
            payInfo = info.getPagePayData();
        } catch (Throwable e) {
            hasErr = true;
            if (e instanceof DataIntegrityViolationException || e instanceof SQLException) {
                payInfo = "data request error!";
            } else {
                payInfo = e.getMessage();
            }
        }
        writeMsg(response, hasErr ? 500 : 200, payInfo);
    }

    @RequestMapping(value = "/notify/{channel}/{product}/{recordId}", method = {RequestMethod.POST, RequestMethod.GET})
    public void notify(@PathVariable String channel,
                       @PathVariable String product,
                       @PathVariable String recordId,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        ProductType productType = null;
        String resultStr = "repeat the callback!", requestMsg = null;
        boolean hasErr = false;
        try {
            productType = ProductType.fromByValue(product);
            tryLock(productType, recordId);
            Map<String, Object> params = PayUtils.parseParams(request);
            TradingContext context = init(recordId, params);
            TradingStatus org = context.getResult().getStatus();
            //忽略重复回调
            if (TradingStatus.PENDING.equals(org)) {
                context.setChannel(Channel.from(channel));
                requestMsg = StreamUtils.toString(request.getInputStream(), Charsets.UTF_8);
                if (StringUtils.isNotEmpty(requestMsg)) {
                    context.getMessage().setAgentRequestData(requestMsg);
                } else {
                    context.getMessage().setAgentRequestData(context.getMessage().getRequestData());
                }

                ComponentDefinition definition = getComponentDefinition(context);
                Pay component = SpringContextHolder.getBean(definition.getName());

                //加载配置信息
                configLoader.load(context);
                TradingResult result = component.handleNotify(context);
                resultStr = (String) result.getData();
                context.getMessage().setAgentResponseData(resultStr);
                if (null == result.getRspTime()) {
                    result.setRspTime(new Date());
                }
                dispatchNotify(result, context);
            }
        } catch (Throwable e) {
            hasErr = true;
            if (e instanceof DataIntegrityViolationException || e instanceof SQLException) {
                resultStr = "data request error!";
            } else {
                resultStr = e.getMessage();
            }
            logger.error("callback handle fail,recordId: " + recordId + ",message: " + requestMsg, e);
        } finally {
            unlock(productType, recordId);
        }
        writeMsg(response, hasErr ? 500 : 200, resultStr);
    }

    @RequestMapping(value = "/direct/{channel}/{product}/{recordId}")
    public void direct(@PathVariable String channel,
                       @PathVariable String product,
                       @PathVariable String recordId,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        ProductType productType = null;
        try {
            productType = ProductType.fromByValue(product);
            tryLock(productType, recordId);
            Map<String, Object> params = PayUtils.parseParams(request);
            TradingContext context = init(recordId, params);
            context.setChannel(Channel.from(channel));
            context.setMethod("trade.query");
            TradingResult result = context.getResult();
            if (TradingStatus.PENDING.equals(result.getStatus())) {
                ComponentDefinition definition = getComponentDefinition(context);
                Pay component = SpringContextHolder.getBean(definition.getName());
                //加载配置信息
                configLoader.load(context);
                context.setTarget(context);
                result = component.doPay(context);
            }

            if (context.getReturnUrl().indexOf('?') > 0) {
                response.sendRedirect(context.getReturnUrl() + "&status=" + result.getStatus());
            } else {
                response.sendRedirect(context.getReturnUrl() + "?status=" + result.getStatus());
            }
        } catch (Throwable e) {
            logger.error("direct handle fail,recordId: " + recordId, e);
            writeMsg(response, 500, "direct handle fail,recordId: " + recordId);
        } finally {
            unlock(productType, recordId);
        }

    }


    /**
     * 获取code
     *
     * @return
     */
    @RequestMapping(value = "/auth/{channelId}/{appId}", method = RequestMethod.GET)
    public void getOpenId(@PathVariable String channelId,
                          @PathVariable String appId,
                          String redirectUrl,
                          String code,
                          HttpServletRequest req,
                          HttpServletResponse rsp) throws IOException {
        if (StringUtils.isEmpty(redirectUrl)) {
            logger.error("redirectUrl not allowed to be empty");
            writeMsg(rsp, 500, "redirectUrl not allowed to be empty!");
            return;
        }
        try {
            TradingContext context = new TradingContext();
            context.setChannel(Channel.from(channelId));
            context.setProductType(ProductType.JSAPI);
            Application application = new Application();
            context.setApplication(application);
            application.setAppId(appId);
            application = payPersist.getApplication(context);
            switch (context.getChannel()) {
                case WXPAY:
                    WxpayConfig config = (WxpayConfig) configLoader.getConfig(application);
                    String openId = CookieUtils.getValue(req, "openId_" + config.getMchId() + config.getAppId());
                    redirectUrl = getWxOpenIdUrl(rsp, application, config, redirectUrl, openId, code);
                    break;
            }
            rsp.sendRedirect(redirectUrl);
        } catch (Throwable e) {
            logger.error("auth openId error, channel: " + channelId + ",appId: " + appId, e);
            writeMsg(rsp, 500, "auth openId error, channel: " + channelId + ",appId: " + appId);
        }
    }

    /**
     * 获取微信授权地址
     *
     * @param application
     * @param redirectUrl
     * @param code
     * @return
     */
    private String getWxOpenIdUrl(HttpServletResponse response,
                                  Application application,
                                  WxpayConfig config,
                                  String redirectUrl,
                                  String openId,
                                  String code) {

        boolean hasOpenId = !StringUtils.isBlank(openId);
        boolean hasCode = !StringUtils.isBlank(code);
        //如果request中包括code，则是微信回调
        if (hasOpenId || hasCode) {
            if (!hasOpenId) {
                openId = getWxOpenId(config, code);
                //存储到会话cookie
                CookieUtils.add("openId_" + config.getMchId() + config.getAppId(), openId, response);
            }
            if (redirectUrl.indexOf('?') > 0) {
                redirectUrl += "&openId=" + openId;
            } else {
                redirectUrl += "?openId=" + openId;
            }
        } else {//oauth获取code
            redirectUrl = RequestUrlUtils.getAuthUrl(application) + "?redirectUrl=" + URLCodec.encode(redirectUrl);
            redirectUrl = String.format(Constants.AUTH_WX_URL, config.getAppId(), URLCodec.encode(redirectUrl));
        }
        return redirectUrl;
    }

    /**
     * 获取微信openId
     *
     * @param config
     * @param code
     * @return
     */
    private String getWxOpenId(WxpayConfig config, String code) {
        String url = String.format(Constants.TOKEN_WX_URL, config.getAppId(), config.getSecretKey(), code);
        Response rsp = httpclient.doRequest(url);
        Map<String, String> result = JSON.parseObject(rsp.getContent(), Map.class);
        String openId = result.get("openid");
        if (null == openId) {
            throw new IllegalArgumentException(rsp.getBody());
        }
        return openId;
    }

    /**
     * 输出html
     *
     * @param response
     * @param code
     * @param msg
     * @throws IOException
     */
    private void writeMsg(HttpServletResponse response, int code, String msg) throws IOException {
        if (msg.contains("<meta charset=\"GBK\"/>")) {
            response.setCharacterEncoding(Charsets.GBK.name());
        } else {
            response.setCharacterEncoding(Charsets.UTF_8.name());
        }
        response.setStatus(code);
        response.getWriter().print(msg);
        response.getWriter().flush();
    }

}