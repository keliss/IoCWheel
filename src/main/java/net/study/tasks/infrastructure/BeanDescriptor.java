package net.study.tasks.infrastructure;

import java.lang.annotation.ElementType;
import java.util.Map;

public class BeanDescriptor {

    private String beanName;
    private Class<?> beanClass;
    private Map<Object, ElementType> injectionPoints;

    public BeanDescriptor() {
    }

    public BeanDescriptor(String beanName, Class<?> beanClass) {
        this.beanName = beanName;
        this.beanClass = beanClass;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public Map<Object, ElementType> getInjectionPoints() {
        return injectionPoints;
    }

    public void setInjectionPoints(Map<Object, ElementType> injectionPoints) {
        this.injectionPoints = injectionPoints;
    }
}
