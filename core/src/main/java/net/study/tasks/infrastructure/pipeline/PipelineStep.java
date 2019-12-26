package net.study.tasks.infrastructure.pipeline;

import net.study.tasks.infrastructure.ApplicationContext;

public interface PipelineStep {

    void apply(ApplicationContext context);
}
