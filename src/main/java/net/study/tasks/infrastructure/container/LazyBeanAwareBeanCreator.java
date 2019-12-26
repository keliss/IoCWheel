package net.study.tasks.infrastructure.container;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.descriptor.BeanDescriptor;
import net.study.tasks.infrastructure.descriptor.BeanDescriptorFactory;

import java.util.Arrays;

public class LazyBeanAwareBeanCreator implements CalleeBeanCreator {

    private BeanCreator callbackCreator;

    public LazyBeanAwareBeanCreator(BeanCreator callbackCreator) {
        this.callbackCreator = callbackCreator;
    }

    @Override
    public Object createBeanWithDependencies(ApplicationContext context, BeanDescriptor descriptor) {
        BeanContainer container = context.getBeanContainer();
        if (descriptor.isLazy() && container.getProxyForClass(descriptor.getBeanClass()).isEmpty()) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(descriptor.getBeanClass());
            enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
                if (!descriptor.isLoaded() && Arrays.asList(descriptor.getBeanClass().getDeclaredMethods()).contains(method)) {
                    callback(context, descriptor);
                }
                return proxy.invokeSuper(obj, args);
            });
            Object proxy = enhancer.create();
            BeanDescriptor proxyDescriptor = BeanDescriptorFactory.createFromClass(proxy.getClass());
            context.getBeanDescriptors().add(proxyDescriptor);
            container.addBean(proxyDescriptor, proxy);
            proxyDescriptor.setLoaded(true);
            return proxy;
        }
        return null;
    }

    @Override
    public Object callback(ApplicationContext context, BeanDescriptor descriptor) {
        return callbackCreator.createBeanWithDependencies(context, descriptor);
    }

    @Override
    public BeanCreator getCallback() {
        return callbackCreator;
    }

    @Override
    public void setCallback(BeanCreator callback) {
        this.callbackCreator = callback;
    }
}
