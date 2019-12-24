package net.study.tasks.infrastructure.pipeline;

import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.container.BeanFactory;

public class BeanCreationStep implements PipelineStep {

    private BeanFactory beanFactory;

    public BeanCreationStep(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public BeanCreationStep() {
        this(new BeanFactory());
    }

    @Override
    public void apply(ApplicationContext context) {
        beanFactory.createBeans(context);
    }
}
