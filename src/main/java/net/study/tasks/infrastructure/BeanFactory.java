package net.study.tasks.infrastructure;

public class BeanFactory {

    private ApplicationContext context;

    public BeanFactory() {
        context = ApplicationContext.getInstance();
    }

    public void createBeans() {

    }
}
