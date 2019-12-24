package net.study.tasks.infrastructure.pipeline;

import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.descriptor.BeanDescriptorFactory;
import net.study.tasks.infrastructure.scanner.AnnotationScanner;
import net.study.tasks.infrastructure.scanner.ComponentAnnotationScanner;

import java.util.Set;
import java.util.stream.Collectors;

public class ComponentScanStep implements PipelineStep {

    private AnnotationScanner<Set<Class<?>>> annotationScanner;

    public ComponentScanStep(AnnotationScanner<Set<Class<?>>> annotationScanner) {
        this.annotationScanner = annotationScanner;
    }

    public ComponentScanStep() {
        this(new ComponentAnnotationScanner());
    }

    @Override
    public void apply(ApplicationContext context) {
        Set<Class<?>> classes = annotationScanner.scan(context);
        context.setBeanDescriptors(classes.stream()
                .map(BeanDescriptorFactory::createFromClass)
                .collect(Collectors.toSet()));
    }
}
