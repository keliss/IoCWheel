package net.study.tasks.infrastructure.container;

import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.descriptor.BeanDescriptor;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BeanContainer {

    private Map<BeanDescriptor, Object> beans;

    private BeanContainer() {
        beans = new HashMap<>();
    }

    private static class BeanContainerHolder {
        private static final BeanContainer INSTANCE = new BeanContainer();
    }

    public static BeanContainer getInstance() {
        return BeanContainerHolder.INSTANCE;
    }

    public void addBean(BeanDescriptor descriptor, Object bean) {
        beans.put(descriptor, bean);
    }

    public void removeBean(BeanDescriptor descriptor) {
        beans.remove(descriptor);
    }

    public Object getBean(BeanDescriptor descriptor) {
        return beans.get(descriptor);
    }

    public List<Map.Entry<BeanDescriptor, Object>> getBeansByClass(Class<?> beanClass) {
        return beans.entrySet().stream()
                .filter(e -> isSameClassOrSubtype(e.getKey(), beanClass) || isProxy(e.getKey(), beanClass))
                .collect(Collectors.toList());
    }

    private boolean isSameClassOrSubtype(BeanDescriptor descriptor, Class<?> beanClass) {
        return descriptor.getBeanClass().equals(beanClass) ||
                new Reflections(ApplicationContext.getInstance().getBasePackageScanClass(), new SubTypesScanner())
                        .getSubTypesOf(beanClass)
                        .contains(descriptor.getBeanClass());
    }

    private boolean isProxy(BeanDescriptor descriptor, Class<?> beanClass) {
        return descriptor.isProxy() && descriptor.getBeanClass().getName().startsWith(beanClass.getName());
    }
}
