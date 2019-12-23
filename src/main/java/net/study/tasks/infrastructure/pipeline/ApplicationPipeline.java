package net.study.tasks.infrastructure.pipeline;

import java.util.ArrayList;
import java.util.List;

public class ApplicationPipeline {

    private List<PipelineStep> steps;

    public void launch() {
        createPipeline();
        steps.forEach(PipelineStep::apply);
    }

    private void createPipeline() {
        steps = new ArrayList<>();
        steps.add(new ComponentScanStep());
        steps.add(new InjectionPointsScanStep());
        steps.add(new BeanCreationStep());
        steps.add(new RunnerExecutionStep());
    }
}
