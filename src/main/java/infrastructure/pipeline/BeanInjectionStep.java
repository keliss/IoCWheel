package infrastructure.pipeline;

import infrastructure.BeanInjector;

public class BeanInjectionStep implements PipelineStep {

    public void apply() {
        new BeanInjector().inject();
    }
}
