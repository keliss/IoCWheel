package net.study.tasks.infrastructure.scanner;

import net.study.tasks.annotation.Component;
import net.study.tasks.infrastructure.ApplicationContext;
import org.reflections.Reflections;

import java.util.Set;

public class ComponentAnnotationScanner implements AnnotationScanner<Set<Class<?>>> {

    @Override
    public Set<Class<?>> scan(ApplicationContext context) {
        Class<?> basePackageScanClass = context.getBasePackageScanClass();
        return new Reflections(basePackageScanClass.getPackageName()).getTypesAnnotatedWith(Component.class);
    }


}
