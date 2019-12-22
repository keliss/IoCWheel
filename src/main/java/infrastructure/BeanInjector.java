package infrastructure;

public class BeanInjector {

    private ApplicationContext context;

    public BeanInjector() {
        context = ApplicationContext.getInstance();
    }

    public void inject() {

    }
}
