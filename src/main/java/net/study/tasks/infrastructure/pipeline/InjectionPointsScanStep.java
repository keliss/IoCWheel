package net.study.tasks.infrastructure.pipeline;

import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.BeanDescriptor;
import net.study.tasks.infrastructure.scanner.AnnotationScanner;
import net.study.tasks.infrastructure.scanner.InjectAnnotationScanner;
import org.javatuples.Triplet;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class InjectionPointsScanStep implements PipelineStep {

    private AnnotationScanner<Map<BeanDescriptor, Triplet<Set<Constructor>, Set<Field>, Set<Method>>>> annotationScanner;

    public InjectionPointsScanStep(AnnotationScanner<Map<BeanDescriptor, Triplet<Set<Constructor>, Set<Field>, Set<Method>>>> annotationScanner) {
        this.annotationScanner = annotationScanner;
    }

    public InjectionPointsScanStep() {
        this(new InjectAnnotationScanner());
    }

    public void apply() {
        ApplicationContext.setInjectionPoints(annotationScanner.scan());
    }
}
