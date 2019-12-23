package net.study.tasks.infrastructure.pipeline;

import net.study.tasks.infrastructure.container.BeanFactory;

public class BeanCreationStep implements PipelineStep {

    private BeanFactory beanFactory;

    public BeanCreationStep(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public BeanCreationStep() {
        this(new BeanFactory());
    }

    public void apply() {
        beanFactory.createBeans();
    }
}
