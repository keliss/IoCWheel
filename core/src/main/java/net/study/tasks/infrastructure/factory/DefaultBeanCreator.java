package net.study.tasks.infrastructure.factory;

import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.BeanContainer;
import net.study.tasks.infrastructure.descriptor.BeanDescriptor;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultBeanCreator implements CustomBeanCreator {

    private ApplicationContext context;
    private List<BeanCreator> customBeanCreators;

    public DefaultBeanCreator() {
        customBeanCreators = new ArrayList<>();
    }

    public DefaultBeanCreator(List<BeanCreator> customBeanCreators) {
        this.customBeanCreators = customBeanCreators;
    }

    @Override
    public Object createBeanWithDependencies(ApplicationContext context, BeanDescriptor descriptor) {
        this.context = context;
        BeanContainer container = context.getBeanContainer();
        Object createdBean;
        createdBean = container.getBean(descriptor);
        if (createdBean != null) {
            return createdBean;
        }
        createdBean = createCustomBean(context, descriptor);
        if (createdBean != null) {
            return createdBean;
        }
        List<Constructor> annotatedConstructors = new ArrayList<>(context.getInjectionPoints().get(descriptor).getConstructors());
        if (annotatedConstructors.size() > 1) {
            throw new IllegalStateException("Component cannot have more than one constructor annotated with @Inject");
        }
        if (annotatedConstructors.isEmpty()) {
            try {
                Constructor<?> defaultConstructor = descriptor.getBeanClass().getConstructor();
                defaultConstructor.setAccessible(true);
                createdBean = defaultConstructor.newInstance();
                container.addBean(descriptor, createdBean);
            } catch (Exception e) {
                throw new RuntimeException("Component should have either default constructor or constructor with dependencies", e);
            }
        } else {
            Constructor<?> constructorWithDependencies = annotatedConstructors.get(0);
            List<Object> dependencies = new ArrayList<>();
            Stream.of(constructorWithDependencies.getParameterTypes()).forEach(p -> {
                dependencies.add(getDependency(constructorWithDependencies.getDeclaringClass(), p));
            });
            try {
                createdBean = constructorWithDependencies.newInstance(dependencies.toArray());
                container.addBean(descriptor, createdBean);
            } catch (Exception e) {
                throw new RuntimeException("Component should have either default constructor or constructor with dependencies", e);
            }
        }
        setFields(descriptor);
        setFieldsThroughSetters(descriptor);
        descriptor.setLoaded(true);
        return createdBean;
    }

    private void setFields(BeanDescriptor descriptor) {
        BeanContainer container = context.getBeanContainer();
        List<Field> annotatedFields = new ArrayList<>(context.getInjectionPoints().get(descriptor).getFields());
        if (annotatedFields.isEmpty()) {
            return;
        }
        annotatedFields.forEach(f -> {
            f.setAccessible(true);
            try {
                f.set(container.getBean(descriptor), getDependency(f.getDeclaringClass(), f.getType()));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Should never be thrown");
            }
        });
    }

    private void setFieldsThroughSetters(BeanDescriptor descriptor) {
        BeanContainer container = context.getBeanContainer();
        List<Method> annotatedSetters = new ArrayList<>(context.getInjectionPoints().get(descriptor).getMethods());
        if (annotatedSetters.isEmpty()) {
            return;
        }
        annotatedSetters.forEach(s -> {
            s.setAccessible(true);
            List<Object> dependencies = new ArrayList<>();
            Stream.of(s.getParameterTypes()).forEach(p -> dependencies.add(getDependency(s.getDeclaringClass(), p)));
            try {
                s.invoke(container.getBean(descriptor), dependencies.toArray());
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException("Should never be thrown");
            }
        });
    }

    private Object getDependency(Class<?> dependantClass, Class<?> dependencyClass) {
        BeanContainer container = context.getBeanContainer();
        List<Map.Entry<BeanDescriptor, Object>> beans = container.getBeansByClass(dependencyClass);
        if (beans.isEmpty()) {
            List<BeanDescriptor> descriptorsForParameter =
                    context.getBeanDescriptors().stream()
                            .filter(d -> d.getBeanClass().equals(dependencyClass) ||
                                    new Reflections(context.getBasePackageScanClass(), new SubTypesScanner())
                                            .getSubTypesOf(dependencyClass)
                                            .contains(d.getBeanClass()))
                            .collect(Collectors.toList());
            if (descriptorsForParameter.size() > 1) {
                throw new RuntimeException("Multiple beans of the same class aren't supported");
            }
            if (descriptorsForParameter.isEmpty()) {
                throw new RuntimeException("Unmet dependency in class " + dependantClass.getName() +
                        ". Couldn't find bean for parameter of type " + dependencyClass.getName());
            }
            return createBeanWithDependencies(context, descriptorsForParameter.get(0));
        } else {
            return beans.get(0).getValue();
        }
    }

    @Override
    public Object createCustomBean(ApplicationContext context, BeanDescriptor descriptor) {
        Object customizedBean = null;
        for (BeanCreator creator : customBeanCreators) {
            customizedBean = creator.createBeanWithDependencies(context, descriptor);
            if (customizedBean != null) {
                break;
            }
        }
        return customizedBean;
    }

    @Override
    public List<BeanCreator> getCustomBeanCreators() {
        return customBeanCreators;
    }

    @Override
    public void setCustomBeanCreators(List<BeanCreator> customBeanCreators) {
        this.customBeanCreators = customBeanCreators;
    }

    @Override
    public void addCustomBeanCreator(BeanCreator customBeanCreator) {
        customBeanCreators.add(customBeanCreator);
    }
}
