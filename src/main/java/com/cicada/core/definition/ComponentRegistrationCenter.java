package com.cicada.core.definition;

import com.cicada.core.annotation.Component;
import com.cicada.core.enums.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付组件注册中心
 *
 * @author: WUJXIAO
 * @create: 2018-12-25 10:58
 * @version: 1.0
 */
public class ComponentRegistrationCenter {

    private final static Map<Channel, ChannelDefinition> channelDefinitionMap = new HashMap<>(32);

    public static void registerComponent(Component component, String name) {

        ChannelDefinition channelDefinition = channelDefinitionMap.get(component.channel());
        if (channelDefinition == null) {
            channelDefinition = new ChannelDefinition(component.channel());
        }

        ComponentDefinition componentDefinition = new ComponentDefinition(component, name);
        channelDefinition.addComponentDefinition(componentDefinition);

        channelDefinitionMap.put(component.channel(), channelDefinition);
    }

    /**
     * <p>组件定义</p>
     *
     * @param channel 渠道
     * @param method  功能名
     * @return
     */
    public static ComponentDefinition getComponentDefinition(Channel channel, String method, String version) {

        ChannelDefinition channelDefinition = channelDefinitionMap.get(channel);

        if (channelDefinition == null) {
            return null;
        }

        ComponentDefinition definition = channelDefinition.getComponentDefinition(method);

        if (definition == null)
            definition = channelDefinition.getComponentDefinition(method + version);

        return definition;
    }


}
