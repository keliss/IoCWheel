package net.study.tasks.infrastructure.pipeline;

import net.study.tasks.infrastructure.scanner.InjectAnnotationScanner;
import org.javatuples.Quartet;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

public class BeanInjectionStep implements PipelineStep {

    public void apply() {
        Set<Quartet<Class<?>, Set<Constructor>, Set<Field>, Set<Method>>> injectionPoints = new InjectAnnotationScanner().scan();

    }
}
