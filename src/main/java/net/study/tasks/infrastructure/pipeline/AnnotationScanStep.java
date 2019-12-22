package net.study.tasks.infrastructure.pipeline;

import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.BeanDescriptorFactory;
import net.study.tasks.infrastructure.scanner.AnnotationScanner;
import net.study.tasks.infrastructure.scanner.ComponentAnnotationScanner;

import java.util.Set;
import java.util.stream.Collectors;

public class AnnotationScanStep implements PipelineStep {

    private AnnotationScanner<Set<Class<?>>> annotationScanner;

    public AnnotationScanStep(AnnotationScanner<Set<Class<?>>> annotationScanner) {
        this.annotationScanner = annotationScanner;
    }

    public AnnotationScanStep() {
        this(new ComponentAnnotationScanner());
    }

    @Override
    public void apply() {
        Set<Class<?>> classes = annotationScanner.scan();
        ApplicationContext.setBeanDescriptors(classes.stream()
                .map(BeanDescriptorFactory::createFromClass)
                .collect(Collectors.toSet()));
    }
}
