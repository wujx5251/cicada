package com.cicada.core.definition;


import com.cicada.core.annotation.Component;
import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ProductType;

/**
 * 支付组件定义
 *
 * @author: WUJXIAO
 * @create: 2018-12-25 10:58
 * @version: 1.0
 */
public class ComponentDefinition {

    private final Channel channel;
    private final ProductType product;
    private final String version;
    private final String method;
    private final String name;

    public ComponentDefinition(Component component, String name) {
        this.channel = component.channel();
        this.product = component.product();
        this.method = component.value().value();
        this.version = null == component.version() ? "" : component.version();
        this.name = name;
    }

    public Channel getChannel() {
        return channel;
    }

    public ProductType getProduct() {
        return product;
    }

    public String getMethod() {
        return method;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}
