package com.cicada.core.auto;

import com.cicada.core.annotation.Component;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;

/**
 * Component自定义组件扫描配置
 * Created by WUJXIAO on 2018/1/4.
 */
public class ComponentScannerConfigurer implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    private String basePackage;

    private Class<? extends Annotation> annotationClass = Component.class;

    private ApplicationContext applicationContext;

    private BeanNameGenerator nameGenerator;


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        ClassPathComponentScanner scanner = new ClassPathComponentScanner(registry);
        scanner.setResourceLoader(applicationContext);
        scanner.setBeanNameGenerator(nameGenerator);
        scanner.setAnnotationClass(annotationClass);
        scanner.setApplicationContext(applicationContext);
        scanner.registerFilters();
        scanner.scan(StringUtils.tokenizeToStringArray(basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // left intentionally blank
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public BeanNameGenerator getNameGenerator() {
        return nameGenerator;
    }

    public void setNameGenerator(BeanNameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }
}
