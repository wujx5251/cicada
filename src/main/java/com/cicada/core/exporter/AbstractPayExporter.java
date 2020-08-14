package com.cicada.core.exporter;

import com.cicada.core.Pay;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.bean.TradingParameter;
import com.cicada.core.builder.TradingContextBuilder;
import com.cicada.core.definition.ComponentDefinition;
import com.cicada.core.enums.ProductType;
import com.cicada.core.enums.TradingStatus;
import com.cicada.core.exception.basic.PayException;
import com.cicada.core.exception.business.IllegalPaySignatureException;
import com.cicada.core.exception.business.MerchantNotFoundException;
import com.cicada.core.exception.internal.GatewayTimeOutException;
import com.cicada.core.generate.Generator;
import com.cicada.storage.bean.MerchantInfo;
import com.cicada.storage.dao.MerchantDao;
import com.cicada.utils.PayUtils;
import com.cicada.utils.SecurityUtils;
import com.cool.core.RequestContext;
import com.dotin.spring.holder.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 支付服务
 *
 * @author: WUJXIAO
 * @create: 2018-12-18 15:58
 * @version: 1.0
 */
public abstract class AbstractPayExporter extends AbstractExporter {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractPayExporter.class);

    @Autowired
    protected Generator generator;
    @Autowired
    protected MerchantDao merchantDao;
    @Autowired
    private TradingContextBuilder contextBuilder;

    @Transactional(noRollbackFor = {Throwable.class})
    public Object invoke(RequestContext req) {
        verify(req.getParams());//请求验签
        TradingContext context = init(req);
        try {
            ComponentDefinition definition = getComponentDefinition(context);
            context.setProductType(definition.getProduct());
            payPersist.saveTrade(context);

            Pay component = SpringContextHolder.getBean(definition.getName());
            configLoader.load(context);//加载配置信息

            return doHandle(context, component);
        } catch (PayException e) {
            if (e instanceof GatewayTimeOutException) {
                //支付接口渠道请求超时属于未知状态，不予处理
                if (null == context.getProductType() ||
                        ProductType.NONE.value().equals(context.getProductType().value())) {
                    context.getResult().setStatus(TradingStatus.FAIL);
                }
            } else {
                context.getResult().setStatus(TradingStatus.FAIL);
            }
            context.getResult().setErrMsg(e.getMessage());
            throw e;
        } finally {
            req.addResultHook(new ResultHook(context));
        }
    }

    protected abstract Object doHandle(TradingContext context, Pay component);


    protected TradingContext init(RequestContext req) {
        TradingContext context = contextBuilder.build(req);
        context.setRecordId(generator.generateNo());
        payPersist.save(context);
        return context;
    }

    /**
     * 验签,并返回application信息
     *
     * @param params
     */
    protected void verify(Map<String, Object> params) {
        String mchId = (String) params.get(TradingParameter.MCH_ID);
        MerchantInfo info = merchantDao.load(mchId);
        if (null == info) {
            throw new MerchantNotFoundException(mchId);
        }
        String sign = (String) params.remove(TradingParameter.SIGNATURE);
        String signStr = PayUtils.getQueryStr(params) + info.getMchKey();
        params.put(TradingParameter.SIGNATURE, sign);
        String sg = SecurityUtils.signMd5sm(signStr);
        if (!sg.equals(sign)) {
            throw new IllegalPaySignatureException();
        }
    }

    protected class ResultHook implements com.cool.core.result.ResultHook {
        private final TradingContext context;

        ResultHook(TradingContext context) {
            this.context = context;
        }

        @Override
        public void execute(Object data) {
            if (context != null) {
                context.getMessage().setResponseData((String) data);
                context.getMessage().setResponseTime(new Date());
                update(context);
            }
        }
    }
}
