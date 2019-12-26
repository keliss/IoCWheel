package net.study.tasks.infrastructure.pipeline;

import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.descriptor.BeanDescriptor;
import net.study.tasks.infrastructure.InjectionPointsHolder;
import net.study.tasks.infrastructure.scanner.AnnotationScanner;
import net.study.tasks.infrastructure.scanner.InjectAnnotationScanner;

import java.util.Map;

public class InjectionPointScanStep implements PipelineStep {

    private AnnotationScanner<Map<BeanDescriptor, InjectionPointsHolder>> annotationScanner;

    public InjectionPointScanStep(AnnotationScanner<Map<BeanDescriptor, InjectionPointsHolder>> annotationScanner) {
        this.annotationScanner = annotationScanner;
    }

    public InjectionPointScanStep() {
        this(new InjectAnnotationScanner());
    }

    @Override
    public void apply(ApplicationContext context) {
        context.setInjectionPoints(annotationScanner.scan(context));
    }
}
