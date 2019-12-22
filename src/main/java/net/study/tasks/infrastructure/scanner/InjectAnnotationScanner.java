package net.study.tasks.infrastructure.scanner;

import net.study.tasks.annotation.Inject;
import net.study.tasks.infrastructure.ApplicationContext;
import org.javatuples.Quartet;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;

public class InjectAnnotationScanner implements AnnotationScanner<Set<Quartet<Class<?>, Set<Constructor>, Set<Field>, Set<Method>>>> {

    @Override
    public Set<Quartet<Class<?>, Set<Constructor>, Set<Field>, Set<Method>>> scan() {
        return ApplicationContext.getBeanDescriptors().stream().map(d -> extractInjectionPoints(d.getBeanClass())).collect(Collectors.toSet());
    }

    private Quartet<Class<?>, Set<Constructor>, Set<Field>, Set<Method>> extractInjectionPoints(Class<?> c) {
        Reflections reflections = new Reflections(c);
        Set<Constructor> constructors = reflections.getConstructorsAnnotatedWith(Inject.class);
        Set<Field> fields = reflections.getFieldsAnnotatedWith(Inject.class);
        Set<Method> methods = reflections.getMethodsAnnotatedWith(Inject.class);
        return new Quartet<>(c, constructors, fields, methods);
    }
}
