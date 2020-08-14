package com.cicada.core.auto;

import com.cicada.core.Pay;
import com.cicada.core.annotation.Component;
import com.cicada.core.definition.ComponentRegistrationCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

/**
 * 支付组件类扫描器
 *
 * @author: WUJXIAO
 * @create: 2018-12-25 10:58
 * @version: 1.0
 */
class ClassPathComponentScanner extends ClassPathBeanDefinitionScanner {

    private static final Logger logger = LoggerFactory.getLogger(ClassPathComponentScanner.class);

    private Class<? extends Annotation> annotationClass;

    private ApplicationContext applicationContext;

    public ClassPathComponentScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    public void registerFilters() {

        if (annotationClass != null) {

            addIncludeFilter(new AnnotationTypeFilter(annotationClass));

        }

        // exclude package-info.java
        addExcludeFilter(new TypeFilter() {

            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {

                String className = metadataReader.getClassMetadata().getClassName();

                return className.endsWith("package-info");
            }
        });
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {

        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        if (beanDefinitions.isEmpty()) {

            logger.warn("No pay component or validator was found in '" + Arrays.toString(basePackages) + "' package. Please check your freemarkerEngineConfiguration.");

        } else {

            processBeanDefinitions(beanDefinitions);
        }

        return beanDefinitions;
    }

    @SuppressWarnings("unchecked")
    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {

        GenericBeanDefinition definition;
        try {
            for (BeanDefinitionHolder holder : beanDefinitions) {
                definition = (GenericBeanDefinition) holder.getBeanDefinition();

                Class<?> resolveClazz = definition.resolveBeanClass(applicationContext.getClassLoader());
                Component componentAnnotation = resolveClazz.getAnnotation(Component.class);
                if (componentAnnotation != null && Pay.class.isAssignableFrom(resolveClazz)) {
                    ComponentRegistrationCenter.registerComponent(componentAnnotation, holder.getBeanName());
                }
            }
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("scan class not found", ex);
        }

    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
