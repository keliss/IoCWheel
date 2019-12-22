package infrastructure;

import java.util.List;

public class ApplicationContext {

    private static List<BeanDescriptor> beanDescriptors;

    private ApplicationContext() {}

    private static class ContextHolder {
        private static ApplicationContext INSTANCE = new ApplicationContext();
    }

    public static ApplicationContext getInstance() {
        return ContextHolder.INSTANCE;
    }

    public static void setBeanDescriptors(List<BeanDescriptor> beanDescriptors) {
        ApplicationContext.beanDescriptors = beanDescriptors;
    }

    public static BeanContainer getBeanContainer() {
        return BeanContainer.getInstance();
    }

    public static List<BeanDescriptor> getBeanDescriptors() {
        return beanDescriptors;
    }
}
