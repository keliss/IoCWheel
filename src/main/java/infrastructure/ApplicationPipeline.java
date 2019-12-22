package infrastructure;

import infrastructure.pipeline.AnnotationScanStep;
import infrastructure.pipeline.BeanCreationStep;
import infrastructure.pipeline.BeanInjectionStep;
import infrastructure.pipeline.PipelineStep;

import java.util.ArrayList;
import java.util.List;

public class ApplicationPipeline {

    private List<PipelineStep> steps;

    // entry point for an application that dares to use this framework
    public void launch() {
        createPipeline();
        steps.forEach(PipelineStep::apply);
    }

    private void createPipeline() {
        steps = new ArrayList<>();
        steps.add(new AnnotationScanStep());
        steps.add(new BeanCreationStep());
        steps.add(new BeanInjectionStep());
    }
}
