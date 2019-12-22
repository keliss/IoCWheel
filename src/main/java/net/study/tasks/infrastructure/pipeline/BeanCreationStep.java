package net.study.tasks.infrastructure.pipeline;

import net.study.tasks.infrastructure.BeanFactory;

public class BeanCreationStep implements PipelineStep {

    public void apply() {
        new BeanFactory().createBeans();
    }
}
