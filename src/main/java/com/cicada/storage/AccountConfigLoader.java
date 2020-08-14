package com.cicada.storage;


import com.cicada.Constants;
import com.cicada.component.alipay.bean.AlipayConfig;
import com.cicada.component.bdpay.bean.BdpayConfig;
import com.cicada.component.jdpay.bean.JdpayConfig;
import com.cicada.component.kqpay.bean.KqpayConfig;
import com.cicada.component.qqpay.bean.QqpayConfig;
import com.cicada.component.unionpay.bean.UnionpayConfig;
import com.cicada.component.wxpay.bean.WxpayConfig;
import com.cicada.core.bean.AccountConfig;
import com.cicada.core.bean.Application;
import com.cicada.core.bean.TradingContext;
import com.cicada.core.enums.ProductType;
import com.cicada.core.exception.business.ChannelConfigurationNotFoundException;
import com.dotin.clotho.bean.Config;
import com.dotin.clotho.client.ConfigClientFactory;
import com.dotin.clotho.core.listener.DataChangeListener;
import com.dotin.common.utils.crypto.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AccountConfigLoader {

    private static final Map<String, AccountConfig> configMap = new ConcurrentHashMap<String, AccountConfig>(64);

    @Autowired
    private PayPersistService persistService;

    public void load(TradingContext context) {
        ProductType type = context.getProductType();
        context.setProductType(getOrigType(context));
        context.setApplication(persistService.getApplication(context));
        context.setConfig(getConfig(context.getApplication()));
        context.setProductType(type);
    }

    public AccountConfig getConfig(Application application) {
        String key = Constants.CONFIG_CHANNEL_ACCOUNT_PREFIX + application.getcId();
        AccountConfig config = configMap.get(key);
        if (null == config) {
            config = load(application);
            if (null != config) {
                configMap.put(key, config);
            }
        }
        return config;
    }

    private AccountConfig load(Application application) {
        String config = ConfigClientFactory.getInstance().getStr(Constants.CONFIG_CHANNEL_ACCOUNT_PREFIX + application.getcId());
        if (null != config) {
            try {
                config = SecurityUtils.decrypt(config, Constants.SECURITY_KEY + application.getSalt());
                switch (application.getChannel()) {
                    case ALIPAY:
                        return AlipayConfig.build(config);
                    case WXPAY:
                        return WxpayConfig.build(config);
                    case UNIONPAY:
                        return UnionpayConfig.build(config);
                    case KQPAY:
                        return KqpayConfig.build(config);
                    case QQPAY:
                        return QqpayConfig.build(config);
                    case JDPAY:
                        return JdpayConfig.build(config);
                    case BDPAY:
                        return BdpayConfig.build(config);
                }
            } catch (Throwable ex) {
                throw new ChannelConfigurationNotFoundException(application.getChannel().value(), ex);
            }
        }
        throw new ChannelConfigurationNotFoundException(application.getChannel().value());
    }

    private ProductType getOrigType(TradingContext context) {
        ProductType type = context.getProductType();
        boolean next = false;
        switch (type) {//一下三类产品类型属于无卡快捷支付特有，故此使用快捷支付配置
            case AUTH:
            case QUICK_PRE:
            case AUTH_CONFIRM:
                next = false;
                type = ProductType.QUICK;
                break;
            case CANCEL:
            case REFUND:
            case NONE:
                next = true;
                type = context.getTarget().getProductType();//使用父级
                break;
        }

        if (next && null != context.getTarget())
            return getOrigType(context.getTarget());
        return type;
    }

    static {
        ConfigClientFactory.addDataChangeListenerList(new DataChangeListener() {
            @Override
            public void call(Config dataSource) {
                configMap.remove(dataSource.getKey());
            }
        });
    }

}
