package com.cicada.core.annotation;


import com.cicada.core.enums.Channel;
import com.cicada.core.enums.ComponetType;
import com.cicada.core.enums.ProductType;

import java.lang.annotation.*;

/**
 * <p>支付组件</p>
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    /**
     * 接口id,对应接口协议中的接口id
     *
     * @return
     */
    ComponetType value();

    /**
     * 支付渠道
     */
    Channel channel();

    /**
     * 版本号
     *
     * @return
     */
    String version() default "";

    /**
     * 产品名字
     */
    ProductType product() default ProductType.NONE;

}
