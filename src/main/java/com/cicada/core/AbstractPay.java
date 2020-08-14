package com.cicada.core;

import com.cicada.Config;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingResult;
import com.cicada.core.enums.TradingStatus;
import com.cicada.core.exception.business.IllegalChanelSignatureException;
import com.cicada.core.requester.RequestExecutor;
import com.cicada.utils.PayUtils;
import com.dotin.common.net.http.Response;
import com.dotin.common.utils.DateUtils;

import java.util.Map;

/**
 * 支付抽象接口
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public abstract class AbstractPay extends AbstractNotify {

    /**
     * 请求参数构建
     *
     * @param context
     * @return
     */
    abstract public <T> Map<String, T> build(TradingContext context);

    protected TradingResult render(TradingContext context, TradingResult result) {
        if (null != context.getOrder()) {
            result.setAmount(context.getOrder().getAmount());
        }
        if (null == result.getAmount() &&
                null != context.getTarget() &&
                null != context.getTarget().getOrder()) {
            result.setAmount(context.getTarget().getOrder().getAmount());
        }
        return result.setStatus(TradingStatus.SUCCESS);
    }

    /**
     * 数据校验
     *
     * @param context
     * @param params
     * @return
     */
    protected <T> boolean verify(TradingContext context, Map<String, T> params) {
        return true;
    }

    /**
     * 获取支付请求执行器
     */
    abstract protected RequestExecutor getRequestExecutor(TradingContext context);


    @Override
    public TradingResult doPay(TradingContext context) {
        Map<String, ?> param = build(context);
        TradingResult result;
        if (true) {
            result = repeat(context, param);
        } else {
            result = execute(context, param);
        }
        return result;
    }

    /**
     * 在连接最大超时时间限制下针对重试数据进行最大程度进行重复请求
     *
     * @param context
     * @param param
     * @return
     */
    private <T> TradingResult repeat(TradingContext context, Map<String, T> param) {
        TradingResult result = null;
        long startTime = System.currentTimeMillis(), remain;

        while (true) {
            if (null != result && logger.isWarnEnabled())
                logger.warn("repeat request params:{}", param);

            result = execute(context, param);

            if (result.isRetry() && TradingStatus.PENDING.equals(result.getStatus())) {
                remain = Config.getReqTimeout() - (System.currentTimeMillis() - startTime);
                if (remain <= 100) {
                    break;
                } else {
                    if (remain > 5 * 1000) {
                        PayUtils.sleep(5 * 1000);
                    } else {
                        PayUtils.sleep(1000L);
                    }
                    continue;
                }
            }
            break;
        }
        return result;
    }

    private <T> TradingResult execute(TradingContext context, Map<String, T> param) {
        RequestExecutor executor = getRequestExecutor(context);
        Object re = param;
        if (null != executor) {
            re = request(executor, context, param);
        }
        TradingResult result = render(context, re);
        result.setRspTime(context.getMessage().getAgentResponseTime());
        return result;
    }

    private <T> Object request(RequestExecutor executor, TradingContext context, Map<String, T> params) {

        context.getMessage().setAgentRequestTime(DateUtils.now());
        Response rsp = executor.execute(context, params);
        context.getMessage().setAgentResponseTime(DateUtils.now());

        Object result = executor.parse(context, rsp);

        if (!executor.verify(context, result)) {
            throw new IllegalChanelSignatureException();
        }
        return result;
    }

}