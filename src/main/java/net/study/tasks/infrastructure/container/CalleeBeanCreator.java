package net.study.tasks.infrastructure.container;

import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.descriptor.BeanDescriptor;

public interface CalleeBeanCreator extends BeanCreator {

    Object callback(ApplicationContext context, BeanDescriptor descriptor);

    BeanCreator getCallback();

    void setCallback(BeanCreator callback);
}
