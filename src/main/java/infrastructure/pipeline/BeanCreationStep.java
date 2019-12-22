package infrastructure.pipeline;

import infrastructure.BeanFactory;

public class BeanCreationStep implements PipelineStep {

    public void apply() {
        new BeanFactory().createBeans();
    }
}
