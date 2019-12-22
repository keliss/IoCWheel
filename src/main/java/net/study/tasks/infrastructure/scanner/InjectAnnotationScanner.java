package net.study.tasks.infrastructure.scanner;

import net.study.tasks.annotation.Inject;
import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.BeanDescriptor;
import org.javatuples.Quartet;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;

public class InjectAnnotationScanner implements AnnotationScanner<Set<Quartet<BeanDescriptor, Set<Constructor>, Set<Field>, Set<Method>>>> {

    @Override
    public Set<Quartet<BeanDescriptor, Set<Constructor>, Set<Field>, Set<Method>>> scan() {
        return ApplicationContext.getBeanDescriptors().stream().map(this::extractInjectionPoints).collect(Collectors.toSet());
    }

    private Quartet<BeanDescriptor, Set<Constructor>, Set<Field>, Set<Method>> extractInjectionPoints(BeanDescriptor descriptor) {
        Reflections reflections = new Reflections(descriptor.getBeanClass());
        Set<Constructor> constructors = reflections.getConstructorsAnnotatedWith(Inject.class);
        Set<Field> fields = reflections.getFieldsAnnotatedWith(Inject.class);
        Set<Method> methods = reflections.getMethodsAnnotatedWith(Inject.class);
        return new Quartet<>(descriptor, constructors, fields, methods);
    }
}
