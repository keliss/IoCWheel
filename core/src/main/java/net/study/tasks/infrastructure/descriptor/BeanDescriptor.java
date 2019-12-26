package net.study.tasks.infrastructure.descriptor;

import java.util.Objects;

public class BeanDescriptor {

    private String beanName;
    private Class<?> beanClass;
    private boolean isLazy;
    private boolean isProxy;
    private boolean isLoaded;

    public BeanDescriptor() {
    }

    public BeanDescriptor(String beanName, Class<?> beanClass, boolean isLazy) {
        this.beanName = beanName;
        this.beanClass = beanClass;
        this.isLazy = isLazy;
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

    public boolean isLazy() {
        return isLazy;
    }

    public void setLazy(boolean lazy) {
        isLazy = lazy;
    }

    public boolean isProxy() {
        return isProxy;
    }

    public void setProxy(boolean proxy) {
        isProxy = proxy;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeanDescriptor that = (BeanDescriptor) o;
        return Objects.equals(beanName, that.beanName) &&
                Objects.equals(beanClass, that.beanClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanName, beanClass);
    }
}
