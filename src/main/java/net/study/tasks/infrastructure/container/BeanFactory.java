package net.study.tasks.infrastructure.container;

import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.descriptor.BeanDescriptor;
import org.javatuples.Triplet;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeanFactory {

    public void createBeans() {
        for (Map.Entry<BeanDescriptor, Triplet<Set<Constructor>, Set<Field>, Set<Method>>> entry : ApplicationContext.getInjectionPoints().entrySet()) {
            createBeanWithDependencies(entry.getKey());
        }
        for (Map.Entry<BeanDescriptor, Triplet<Set<Constructor>, Set<Field>, Set<Method>>> entry : ApplicationContext.getInjectionPoints().entrySet()) {
            setFields(entry.getKey());
            setFieldsThroughSetters(entry.getKey());
        }
    }

    private void createBeanWithDependencies(BeanDescriptor descriptor) {
        BeanContainer container = ApplicationContext.getBeanContainer();
        List<Constructor> annotatedConstructors = new ArrayList<>(ApplicationContext.getInjectionPoints().get(descriptor).getValue0());
        if (annotatedConstructors.size() > 1) {
            throw new IllegalStateException("Component cannot have more than one constructor annotated with @Inject");
        }
        if (annotatedConstructors.isEmpty()) {
            try {
                Constructor<?> defaultConstructor = descriptor.getBeanClass().getConstructor();
                defaultConstructor.setAccessible(true);
                container.addBean(descriptor, defaultConstructor.newInstance());
            } catch (Exception e) {
                throw new RuntimeException("Component should have either default constructor or constructor with dependencies", e);
            }
        } else {
            Constructor<?> constructorWithDependencies = annotatedConstructors.get(0);
            List<Object> dependencies = new ArrayList<>();
            Stream.of(constructorWithDependencies.getParameterTypes()).forEach(p -> {
                List<Map.Entry<BeanDescriptor, Object>> beans = container.getBeansByClass(p);
                if (beans.isEmpty()) {
                    List<BeanDescriptor> descriptorsForParameters = ApplicationContext.getBeanDescriptors().stream()
                            .filter(d -> d.getBeanClass().equals(p))
                            .collect(Collectors.toList());
                    ApplicationContext.getBeanDescriptors().stream()
                            .filter(d -> new Reflections(ApplicationContext.getBasePackageScanClass(), new SubTypesScanner()).getSubTypesOf(p).contains(d.getBeanClass()))
                            .forEach(descriptorsForParameters::add);
                    if (descriptorsForParameters.size() > 1) {
                        throw new RuntimeException("Multiple beans of the same class aren't supported");
                    }
                    createBeanWithDependencies(descriptorsForParameters.get(0));
                    dependencies.add(container.getBeansByClass(p).get(0).getValue());
                } else {
                    dependencies.add(beans.get(0).getValue());
                }
            });
            try {
                container.addBean(descriptor, constructorWithDependencies.newInstance(dependencies.toArray()));
            } catch (Exception e) {
                throw new RuntimeException("Component should have either default constructor or constructor with dependencies", e);
            }
        }
    }

    private void setFields(BeanDescriptor descriptor) {
        BeanContainer container = ApplicationContext.getBeanContainer();
        List<Field> annotatedFields = new ArrayList<>(ApplicationContext.getInjectionPoints().get(descriptor).getValue1());
        if (annotatedFields.isEmpty()) {
            return;
        }
        annotatedFields.forEach(f -> {
            f.setAccessible(true);
            try {
                f.set(container.getBean(descriptor), container.getBeansByClass(f.getType()).get(0).getValue());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setFieldsThroughSetters(BeanDescriptor descriptor) {
        BeanContainer container = ApplicationContext.getBeanContainer();
        List<Method> annotatedSetters = new ArrayList<>(ApplicationContext.getInjectionPoints().get(descriptor).getValue2());
        if (annotatedSetters.isEmpty()) {
            return;
        }
        annotatedSetters.forEach(s -> {
            s.setAccessible(true);
            try {
                List<Object> dependencies = new ArrayList<>();
                Stream.of(s.getParameterTypes()).forEach(p -> dependencies.add(container.getBeansByClass(p).get(0).getValue()));
                s.invoke(container.getBean(descriptor), dependencies.toArray());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
