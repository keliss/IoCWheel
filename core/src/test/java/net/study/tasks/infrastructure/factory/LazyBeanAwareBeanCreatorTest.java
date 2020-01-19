package net.study.tasks.infrastructure.factory;

import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.descriptor.BeanDescriptor;
import net.study.tasks.infrastructure.model.TestBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

public class LazyBeanAwareBeanCreatorTest {

    private ApplicationContext context;
    private BeanCreator creator;

    @BeforeEach
    public void setup() {
        context = ApplicationContext.getInstance();
        context.clearContext();
        context.getBeanContainer().clearContainer();
        DefaultBeanCreator defaultBeanCreator = mock(DefaultBeanCreator.class);
        when(defaultBeanCreator.createBeanWithDependencies(eq(context), anyObject())).thenReturn(new TestBean());
        creator = new LazyBeanAwareBeanCreator(defaultBeanCreator);
    }

    @Test
    public void lazyBeanCreatedSuccessfullyTest() {
        BeanDescriptor lazyBeanDescriptor = new BeanDescriptor("lazy", TestBean.class, true);

        TestBean result = (TestBean) creator.createBeanWithDependencies(context, lazyBeanDescriptor);

        verifyNoInteractions(((CalleeBeanCreator) creator).getCallback());
        assertEquals(context.getBeanContainer().getProxyForClass(TestBean.class).get().getValue(), result);
        assertTrue(context.getBeanDescriptors().stream().anyMatch(d -> d.isProxy() && d.getBeanClass().getName().startsWith(TestBean.class.getName())));
        assertNull(context.getBeanContainer().getBean(lazyBeanDescriptor));
        assertFalse(lazyBeanDescriptor.isLoaded());
    }

    @Test
    public void originalBeanCreatedAfterCallingMethodOnProxyTest() {
        BeanDescriptor lazyBeanDescriptor = new BeanDescriptor("lazy", TestBean.class, true);

        TestBean result = (TestBean) creator.createBeanWithDependencies(context, lazyBeanDescriptor);
        result.setSomeIntField(42);

        verify(((CalleeBeanCreator) creator).getCallback(), times(1)).createBeanWithDependencies(context, lazyBeanDescriptor);
    }
}
