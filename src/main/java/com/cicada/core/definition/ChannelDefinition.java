package com.cicada.core.definition;


import com.cicada.core.enums.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付组件
 *
 * @author: WUJXIAO
 * @create: 2018-12-25 10:58
 * @version: 1.0
 */
public class ChannelDefinition {

    private Channel channel;

    private Map<String, ComponentDefinition> componentDefinitionMap = new HashMap<>();

    public ChannelDefinition(Channel channel) {
        this.channel = channel;
    }

    /**
     * 增加支付组件定义
     *
     * @param componentDefinition 支付产品定义
     */
    public void addComponentDefinition(ComponentDefinition componentDefinition) {

        String name = componentDefinition.getMethod() + componentDefinition.getVersion();

        if (channel != componentDefinition.getChannel()) {
            throw new RuntimeException("Channel not matched!");
        } else if (componentDefinitionMap.containsKey(name)) {
            throw new RuntimeException("component existing in " + componentDefinition.getChannel().value() + "channel,[" + componentDefinition.getMethod() + "]!");
        }

        componentDefinitionMap.put(name, componentDefinition);
    }

    public ComponentDefinition getComponentDefinition(String method) {

        return componentDefinitionMap.get(method);
    }


}
