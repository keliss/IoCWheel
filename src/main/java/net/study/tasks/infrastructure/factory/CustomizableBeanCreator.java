package net.study.tasks.infrastructure.factory;

import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.descriptor.BeanDescriptor;

import java.util.List;

public interface CustomizableBeanCreator extends BeanCreator {

    Object customize(ApplicationContext context, BeanDescriptor descriptor);

    List<BeanCreator> getCustomizers();

    void setCustomizers(List<BeanCreator> customizers);

    void addCustomizer(BeanCreator customizer);
}
