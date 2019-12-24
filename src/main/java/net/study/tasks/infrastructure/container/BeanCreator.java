package net.study.tasks.infrastructure.container;

import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.descriptor.BeanDescriptor;

public interface BeanCreator {

    Object createBeanWithDependencies(ApplicationContext context, BeanDescriptor descriptor);
}
