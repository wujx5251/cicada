package com.cicada.core.exporter;

import com.alibaba.fastjson.JSON;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingMessage;
import com.cicada.core.bean.TradingParameter;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.builder.CallbackParamsBuilder;
import com.cicada.core.enums.CallbackStatus;
import com.cicada.core.enums.CallbackType;
import com.cicada.core.enums.ProductType;
import com.cicada.core.enums.TradingStatus;
import com.cicada.storage.CallbackPersistService;
import com.cicada.storage.bean.CallbackDetail;
import com.cicada.storage.dao.RecordDetailDao;
import com.cicada.utils.PayUtils;
import com.cicada.utils.SecurityUtils;
import com.dotin.common.net.http.HttpClient;
import com.dotin.common.net.http.HttpUrlClient;
import com.dotin.common.net.http.Response;
import com.dotin.common.utils.StringUtils;
import com.dotin.thread.AbstractAsyncHandler;
import com.dotin.thread.CommonThreadPool;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * @author WUJXIAO
 * @version 1.0
 * @create 2019-01-09 15:39
 */
public abstract class AbstractNotifyExporter extends AbstractExporter {

    @Autowired
    protected RecordDetailDao detailDao;
    @Autowired
    protected CallbackPersistService persistService;
    @Autowired
    protected CallbackParamsBuilder builder;

    private static final HttpClient client = new HttpUrlClient();

    protected TradingContext init(String recordId, Map<String, Object> params) {
        TradingContext context = payPersist.getContext(recordId, null);
        TradingMessage message = new TradingMessage(params.isEmpty() ? null : params);
        message.setReqReserved(context.getMessage().getReqReserved());
        context.setMessage(message);
        message.setAgentRequestTime(new Date());
        context.setMethod(getMethod(context.getProductType()));
        return context;
    }

    /**
     * 渠道异步通知处理
     *
     * @param result
     * @param context
     */
    protected void dispatchNotify(TradingResult result, TradingContext context) {
        if (!TradingStatus.PENDING.equals(result.getStatus())) {
            TradingStatus org = context.getResult().getStatus();
            context.setResult(result);
            //记录通知
            Map<String, Object> params = builder.build(context);
            context.getMessage().setRequestData(JSON.toJSONString(params));

            long backId = persistService.saveNotify(context, CallbackType.CHANNEL);

            if (TradingStatus.PENDING.equals(org)) {
                payPersist.updateRecord(context);
                //更新订单状态
                payPersist.updateTrade(context);
            }

            params.put(TradingParameter.TIMESTAMP, System.currentTimeMillis());
            String signStr = PayUtils.getQueryStr(params) + context.getApplication().getMchKey();
            params.put(TradingParameter.SIGNATURE, SecurityUtils.signMd5sm(signStr));
            //回调----
            doCallback(context.getNotifyUrl(), params, backId);
        }
    }

    /**
     * 业务系统异步回调
     *
     * @param url
     * @param params
     * @param backId
     */
    private void doCallback(final String url, final Map<String, Object> params, final long backId) {
        CommonThreadPool.execute(new AbstractAsyncHandler() {
            @Override
            public Object call() throws Exception {
                CallbackDetail detail = new CallbackDetail();
                detail.setCallbackId(backId);
                detail.setCallbackTime(new Date());
                Response rsp = null;
                try {
                    rsp = client.doRequest(url, params);
                } catch (Throwable e) {
                    logger.error("call back error,for url：" + url, e);
                }
                if (rsp != null && rsp.getCode() == 200 &&
                        TradingParameter.SUCCESS.equals(rsp.getBody())) {
                    detail.setFinish(CallbackStatus.SUCCESS.value());
                } else {
                    detail.setFinish(CallbackStatus.PENDING.value());
                }
                detail.setCallbackReturnData(null == rsp ? "" : rsp.getBody());
                detail.setCallbackReturnTime(new Date());
                persistService.saveCallback(detail);
                return null;
            }
        });
    }


    /**
     * 获取接口
     *
     * @param productType
     * @return
     */
    protected String getMethod(ProductType productType) {
        String mathod = "trade.";
        switch (productType) {
            case APP:
            case WEB:
            case H5:
            case JSAPI:
            case QRCODE:
            case SCAN:
            case QUICK:
            case AUTH:
                mathod = StringUtils.concat(mathod, productType.value(), ".pay");
                break;
            case AUTH_CONFIRM:
                mathod = StringUtils.concat(mathod, "auth.cfm.pay");
                break;
            case CANCEL:
            case REFUND:
                mathod = StringUtils.concat(mathod, productType.value());
                break;
        }
        return mathod;
    }

}