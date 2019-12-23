package net.study.tasks.infrastructure;

import org.javatuples.Triplet;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ApplicationContext {

    private static Set<BeanDescriptor> beanDescriptors;
    private static Class<?> basePackageScanClass;
    private static Map<BeanDescriptor, Triplet<Set<Constructor>, Set<Field>, Set<Method>>> injectionPoints;

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

    public static Map<BeanDescriptor, Triplet<Set<Constructor>, Set<Field>, Set<Method>>> getInjectionPoints() {
        return injectionPoints;
    }

    public static void setInjectionPoints(Map<BeanDescriptor, Triplet<Set<Constructor>, Set<Field>, Set<Method>>> injectionPoints) {
        ApplicationContext.injectionPoints = injectionPoints;
    }
}
