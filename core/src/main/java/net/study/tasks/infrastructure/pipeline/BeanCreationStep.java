package net.study.tasks.infrastructure.pipeline;

import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.factory.BeanCreator;
import net.study.tasks.infrastructure.factory.BeanFactory;
import net.study.tasks.infrastructure.factory.CustomizableBeanCreator;
import net.study.tasks.infrastructure.factory.DefaultBeanCreator;
import net.study.tasks.infrastructure.factory.LazyBeanAwareBeanCreator;

public class BeanCreationStep implements PipelineStep {

    private BeanFactory beanFactory;

    public BeanCreationStep(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public BeanCreationStep() {
        CustomizableBeanCreator defaultCreator = new DefaultBeanCreator();
        BeanCreator lazyAwareCreator = new LazyBeanAwareBeanCreator(defaultCreator);
        defaultCreator.addCustomizer(lazyAwareCreator);
        this.beanFactory = new BeanFactory(defaultCreator);
    }

    @Override
    public void apply(ApplicationContext context) {
        beanFactory.createBeans(context);
    }
}
