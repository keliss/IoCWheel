package net.study.tasks.infrastructure;

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
                .filter(e ->
                    e.getKey().getBeanClass().equals(beanClass) ||
                            new Reflections(ApplicationContext.getBasePackageScanClass(), new SubTypesScanner())
                                    .getSubTypesOf(beanClass)
                                    .contains(e.getKey().getBeanClass())
                )
                .collect(Collectors.toList());
    }
}
