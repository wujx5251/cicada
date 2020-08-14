package com.cicada.core.exporter;

import com.cicada.Config;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.definition.ComponentDefinition;
import com.cicada.core.definition.ComponentRegistrationCenter;
import com.cicada.core.enums.ProductType;
import com.cicada.core.exception.internal.ComponentNotDefinitionException;
import com.cicada.core.exception.internal.TryLockException;
import com.cicada.storage.AccountConfigLoader;
import com.cicada.storage.PayPersistService;
import com.dotin.lock.DistributeLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 支付服务
 *
 * @author: WUJXIAO
 * @create: 2018-12-18 15:58
 * @version: 1.0
 */
public abstract class AbstractExporter {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractExporter.class);

    @Autowired
    protected PayPersistService payPersist;
    @Autowired
    protected AccountConfigLoader configLoader;
    @Autowired
    private DistributeLock lock;

    /**
     * 上锁
     *
     * @param productType 支付产品类型
     */
    protected void tryLock(ProductType productType, String paySerialNumber) {

        if (null != productType && !ProductType.NONE.value().equals(productType.value())) {

            if (!lock.tryLock(paySerialNumber, Config.getLockConfig())) {

                throw new TryLockException("Try lock '" + productType.value() + "' recordId '" + paySerialNumber + "' error.");
            }
        }
    }

    /**
     * 解锁
     *
     * @param productType     支付产品类型
     * @param paySerialNumber 支付序列号
     */
    protected void unlock(ProductType productType, String paySerialNumber) {
        if (null != productType && !ProductType.NONE.value().equals(productType.value())) {
            lock.unlock(paySerialNumber);
        }
    }

    protected ComponentDefinition getComponentDefinition(TradingContext context) {
        ComponentDefinition definition = ComponentRegistrationCenter.getComponentDefinition(context.getChannel(),
                context.getMethod(), context.getVersion());
        if (null == definition)
            throw new ComponentNotDefinitionException(context.getChannel().value(), context.getMethod());
        return definition;
    }

    protected void update(TradingContext context) {
        try {
            payPersist.update(context);
        } catch (Throwable e) {
            logger.error("pay data update fail", e);
        }
    }

}
