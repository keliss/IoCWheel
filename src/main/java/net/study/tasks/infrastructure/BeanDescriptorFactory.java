package net.study.tasks.infrastructure;

import net.study.tasks.annotation.Component;

public class BeanDescriptorFactory {

    public static BeanDescriptor createFromClass(Class<?> c) {
        String beanName = c.getAnnotation(Component.class).name();
        BeanDescriptor descriptor = new BeanDescriptor(beanName, c);
        if (beanName.isEmpty()) {
            descriptor.setBeanName(c.getName());
        }
        return descriptor;
    }
}
