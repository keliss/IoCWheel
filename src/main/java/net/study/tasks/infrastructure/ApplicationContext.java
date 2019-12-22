package net.study.tasks.infrastructure;

import java.lang.annotation.ElementType;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ApplicationContext {

    private static Set<BeanDescriptor> beanDescriptors;
    private static Class<?> basePackageScanClass;
    private static Map<Object, ElementType> injectionPoints;

    private ApplicationContext() {
        beanDescriptors = new HashSet<>();
    }

    private static class ContextHolder {
        private static ApplicationContext INSTANCE = new ApplicationContext();
    }

    public static ApplicationContext getInstance() {
        return ContextHolder.INSTANCE;
    }

    public static Class<?> getBasePackageScanClass() {
        return basePackageScanClass;
    }

    public static void setBasePackageScanClass(Class<?> basePackageScanClass) {
        ApplicationContext.basePackageScanClass = basePackageScanClass;
    }

    public static BeanContainer getBeanContainer() {
        return BeanContainer.getInstance();
    }

    public static Set<BeanDescriptor> getBeanDescriptors() {
        return beanDescriptors;
    }

    public static void setBeanDescriptors(Set<BeanDescriptor> beanDescriptors) {
        ApplicationContext.beanDescriptors = beanDescriptors;
    }

    public static Map<Object, ElementType> getInjectionPoints() {
        return injectionPoints;
    }

    public static void setInjectionPoints(Map<Object, ElementType> injectionPoints) {
        ApplicationContext.injectionPoints = injectionPoints;
    }
}
