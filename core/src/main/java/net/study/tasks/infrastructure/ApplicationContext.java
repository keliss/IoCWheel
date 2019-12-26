package net.study.tasks.infrastructure;

import net.study.tasks.infrastructure.descriptor.BeanDescriptor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ApplicationContext {

    private static Set<BeanDescriptor> beanDescriptors;
    private static Class<?> basePackageScanClass;
    private static Map<BeanDescriptor, InjectionPointsWrapper> injectionPoints;

    private ApplicationContext() {
        beanDescriptors = new HashSet<>();
    }

    private static class ContextHolder {
        private static ApplicationContext INSTANCE = new ApplicationContext();
    }

    public static ApplicationContext getInstance() {
        return ContextHolder.INSTANCE;
    }

    public Class<?> getBasePackageScanClass() {
        return basePackageScanClass;
    }

    public void setBasePackageScanClass(Class<?> basePackageScanClass) {
        ApplicationContext.basePackageScanClass = basePackageScanClass;
    }

    public BeanContainer getBeanContainer() {
        return BeanContainer.getInstance();
    }

    public Set<BeanDescriptor> getBeanDescriptors() {
        return beanDescriptors;
    }

    public void setBeanDescriptors(Set<BeanDescriptor> beanDescriptors) {
        ApplicationContext.beanDescriptors = beanDescriptors;
    }

    public Map<BeanDescriptor, InjectionPointsWrapper> getInjectionPoints() {
        return injectionPoints;
    }

    public void setInjectionPoints(Map<BeanDescriptor, InjectionPointsWrapper> injectionPoints) {
        ApplicationContext.injectionPoints = injectionPoints;
    }
}
