package net.study.tasks.infrastructure.factory;

import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.descriptor.BeanDescriptor;
import net.study.tasks.infrastructure.InjectionPointsWrapper;

import java.util.Map;

public class BeanFactory {

    private BeanCreator beanCreator;

    public BeanFactory(BeanCreator beanCreator) {
        this.beanCreator = beanCreator;
    }

    public void createBeans(ApplicationContext context) {
        for (Map.Entry<BeanDescriptor, InjectionPointsWrapper> entry : context.getInjectionPoints().entrySet()) {
            beanCreator.createBeanWithDependencies(context, entry.getKey());
        }
    }
}
