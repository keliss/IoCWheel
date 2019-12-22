package net.study.tasks.infrastructure;

import java.util.HashMap;
import java.util.Map;

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

    public void getBean(BeanDescriptor descriptor) {
        beans.get(descriptor);
    }
}
