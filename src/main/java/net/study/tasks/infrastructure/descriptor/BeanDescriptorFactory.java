package net.study.tasks.infrastructure.descriptor;

import net.study.tasks.annotation.Component;

public class BeanDescriptorFactory {

    public static BeanDescriptor createFromClass(Class<?> c) {
        Component component = c.getAnnotation(Component.class);
        BeanDescriptor descriptor = new BeanDescriptor();
        if (component != null) {
            String beanName = component.name();
            descriptor.setBeanName(beanName.isEmpty() ? c.getSimpleName() : beanName);
            descriptor.setLazy(component.lazy());
        } else {
            descriptor.setBeanName(c.getSimpleName());
            descriptor.setProxy(true);
        }
        descriptor.setBeanClass(c);
        return descriptor;
    }
}
