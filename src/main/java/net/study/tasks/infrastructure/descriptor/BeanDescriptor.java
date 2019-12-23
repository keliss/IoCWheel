package net.study.tasks.infrastructure.descriptor;

import java.util.Objects;

public class BeanDescriptor {

    private String beanName;
    private Class<?> beanClass;

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
