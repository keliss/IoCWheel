package net.study.tasks.infrastructure.pipeline;

import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.container.BeanFactory;
import net.study.tasks.infrastructure.container.LazyBeanAwareBeanCreator;

public class BeanCreationStep implements PipelineStep {

    private BeanFactory beanFactory;

    public BeanCreationStep(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public BeanCreationStep() {
        this.beanFactory = new BeanFactory(new LazyBeanAwareBeanCreator());
    }

    @Override
    public void apply(ApplicationContext context) {
        beanFactory.createBeans(context);
    }
}
