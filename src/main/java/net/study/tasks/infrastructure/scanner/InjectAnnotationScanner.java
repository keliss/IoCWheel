package net.study.tasks.infrastructure.scanner;

import net.study.tasks.annotation.Inject;
import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.descriptor.BeanDescriptor;
import net.study.tasks.infrastructure.InjectionPointsHolder;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InjectAnnotationScanner implements AnnotationScanner<Map<BeanDescriptor, InjectionPointsHolder>> {

    @Override
    public Map<BeanDescriptor, InjectionPointsHolder> scan(ApplicationContext context) {
        HashMap<BeanDescriptor, InjectionPointsHolder> map = new HashMap<>();
        context.getBeanDescriptors().forEach(d -> {
            Reflections reflections = new Reflections(
                    new ConfigurationBuilder()
                            .setScanners(new MethodAnnotationsScanner(), new FieldAnnotationsScanner())
                            .filterInputsBy(i -> i.contains(d.getBeanClass().getSimpleName()))
                            .setUrls(ClasspathHelper.forClass(d.getBeanClass()))
            );
            Set<Constructor> constructors = reflections.getConstructorsAnnotatedWith(Inject.class);
            Set<Field> fields = reflections.getFieldsAnnotatedWith(Inject.class);
            Set<Method> methods = reflections.getMethodsAnnotatedWith(Inject.class);
            map.put(d, new InjectionPointsHolder(constructors, fields, methods));
        });
        return map;
    }
}
