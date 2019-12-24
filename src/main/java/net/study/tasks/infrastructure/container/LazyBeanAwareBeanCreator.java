package net.study.tasks.infrastructure.container;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.descriptor.BeanDescriptor;
import net.study.tasks.infrastructure.descriptor.BeanDescriptorFactory;

import java.util.Arrays;

public class LazyBeanAwareBeanCreator extends DefaultBeanCreator {

    @Override
    public Object createBeanWithDependencies(ApplicationContext context, BeanDescriptor descriptor) {
        if (descriptor.isLazy()) {
            BeanContainer container = context.getBeanContainer();
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(descriptor.getBeanClass());
            enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
                if (Arrays.asList(descriptor.getBeanClass().getDeclaredMethods()).contains(method)) {
                    return super.createBeanWithDependencies(context, descriptor);
                }
                return proxy.invokeSuper(obj, args);
            });
            Object proxy = enhancer.create();
            BeanDescriptor proxyDescriptor = BeanDescriptorFactory.createFromClass(proxy.getClass());
            context.getBeanDescriptors().add(proxyDescriptor);
            container.addBean(proxyDescriptor, proxy);
            return proxy;
        } else {
            return super.createBeanWithDependencies(context, descriptor);
        }
    }

}
