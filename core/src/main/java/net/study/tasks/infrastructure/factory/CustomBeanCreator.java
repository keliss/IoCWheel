package net.study.tasks.infrastructure.factory;

import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.descriptor.BeanDescriptor;

import java.util.List;

public interface CustomBeanCreator extends BeanCreator {

    Object createCustomBean(ApplicationContext context, BeanDescriptor descriptor);

    List<BeanCreator> getCustomBeanCreators();

    void setCustomBeanCreators(List<BeanCreator> customizers);

    void addCustomBeanCreator(BeanCreator customizer);
}
