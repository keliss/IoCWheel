package net.study.tasks.infrastructure.factory;

import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.InjectionPointsWrapper;
import net.study.tasks.infrastructure.descriptor.BeanDescriptor;
import net.study.tasks.infrastructure.model.DependencyBean;
import net.study.tasks.infrastructure.model.TestBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultBeanCreatorTest {

    private ApplicationContext context;
    private BeanCreator creator;

    @BeforeEach
    public void setup() {
        context = ApplicationContext.getInstance();
        context.clearContext();
        context.getBeanContainer().clearContainer();
        creator = new DefaultBeanCreator();
    }

    @Test
    public void beanWithDependencyInConstructorCreatedSuccessfullyTest() throws Exception {
        Map<BeanDescriptor, InjectionPointsWrapper> injectionPoints = new HashMap<>();
        DependencyBean dependencyBean = new DependencyBean(12, "abc");
        TestBean testBean = new TestBean(dependencyBean);
        testBean.setSomeIntField(4);
        testBean.setSomeStringField("test");
        Constructor<?> constructor = testBean.getClass().getConstructor(DependencyBean.class);
        InjectionPointsWrapper wrapper = new InjectionPointsWrapper(Set.of(constructor), Set.of(), Set.of());
        BeanDescriptor descriptorForDependantBean = new BeanDescriptor("dependant", testBean.getClass(), false);
        BeanDescriptor descriptorForDependencyBean = new BeanDescriptor("dependency", dependencyBean.getClass(), false);
        injectionPoints.put(descriptorForDependantBean, wrapper);
        context.setInjectionPoints(injectionPoints);
        context.getBeanDescriptors().add(descriptorForDependantBean);
        context.getBeanDescriptors().add(descriptorForDependencyBean);
        context.getBeanContainer().addBean(descriptorForDependencyBean, dependencyBean);

        TestBean result = (TestBean) creator.createBeanWithDependencies(context, descriptorForDependantBean);

        assertEquals(dependencyBean, result.getDependency());
        assertNotEquals(testBean, result);
        assertEquals(result, context.getBeanContainer().getBean(descriptorForDependantBean));
    }

    @Test
    public void beanWithDependencyInFieldCreatedSuccessfullyTest() throws Exception {
        Map<BeanDescriptor, InjectionPointsWrapper> injectionPoints = new HashMap<>();
        DependencyBean dependencyBean = new DependencyBean(12, "abc");
        TestBean testBean = new TestBean(dependencyBean);
        testBean.setSomeIntField(4);
        testBean.setSomeStringField("test");
        Field dependencyField = testBean.getClass().getDeclaredField("dependency");
        InjectionPointsWrapper wrapper = new InjectionPointsWrapper(Set.of(), Set.of(dependencyField), Set.of());
        BeanDescriptor descriptorForDependantBean = new BeanDescriptor("dependant", testBean.getClass(), false);
        BeanDescriptor descriptorForDependencyBean = new BeanDescriptor("dependency", dependencyBean.getClass(), false);
        injectionPoints.put(descriptorForDependantBean, wrapper);
        context.setInjectionPoints(injectionPoints);
        context.getBeanDescriptors().add(descriptorForDependantBean);
        context.getBeanDescriptors().add(descriptorForDependencyBean);
        context.getBeanContainer().addBean(descriptorForDependencyBean, dependencyBean);

        TestBean result = (TestBean) creator.createBeanWithDependencies(context, descriptorForDependantBean);

        assertEquals(dependencyBean, result.getDependency());
        assertNotEquals(testBean, result);
        assertEquals(result, context.getBeanContainer().getBean(descriptorForDependantBean));
    }

    @Test
    public void beanWithDependencyInSetterCreatedSuccessfullyTest() throws Exception {
        Map<BeanDescriptor, InjectionPointsWrapper> injectionPoints = new HashMap<>();
        DependencyBean dependencyBean = new DependencyBean(12, "abc");
        TestBean testBean = new TestBean(dependencyBean);
        testBean.setSomeIntField(4);
        testBean.setSomeStringField("test");
        Method dependencySetter = testBean.getClass().getDeclaredMethod("setDependency", DependencyBean.class);
        InjectionPointsWrapper wrapper = new InjectionPointsWrapper(Set.of(), Set.of(), Set.of(dependencySetter));
        BeanDescriptor descriptorForDependantBean = new BeanDescriptor("dependant", testBean.getClass(), false);
        BeanDescriptor descriptorForDependencyBean = new BeanDescriptor("dependency", dependencyBean.getClass(), false);
        injectionPoints.put(descriptorForDependantBean, wrapper);
        context.setInjectionPoints(injectionPoints);
        context.getBeanDescriptors().add(descriptorForDependantBean);
        context.getBeanDescriptors().add(descriptorForDependencyBean);
        context.getBeanContainer().addBean(descriptorForDependencyBean, dependencyBean);

        TestBean result = (TestBean) creator.createBeanWithDependencies(context, descriptorForDependantBean);

        assertEquals(dependencyBean, result.getDependency());
        assertNotEquals(testBean, result);
        assertEquals(result, context.getBeanContainer().getBean(descriptorForDependantBean));
    }

    @Test
    public void beanWithDependencyInConstructorCreatedRecursivelyTest() throws Exception {
        Map<BeanDescriptor, InjectionPointsWrapper> injectionPoints = new HashMap<>();
        DependencyBean dependencyBean = new DependencyBean(12, "abc");
        TestBean testBean = new TestBean(dependencyBean);
        testBean.setSomeIntField(4);
        testBean.setSomeStringField("test");
        Constructor<?> constructor = testBean.getClass().getConstructor(DependencyBean.class);
        InjectionPointsWrapper dependandWrapper = new InjectionPointsWrapper(Set.of(constructor), Set.of(), Set.of());
        InjectionPointsWrapper dependencyWrapper = new InjectionPointsWrapper(Set.of(), Set.of(), Set.of());
        BeanDescriptor descriptorForDependantBean = new BeanDescriptor("dependant", testBean.getClass(), false);
        BeanDescriptor descriptorForDependencyBean = new BeanDescriptor("dependency", dependencyBean.getClass(), false);
        injectionPoints.put(descriptorForDependantBean, dependandWrapper);
        injectionPoints.put(descriptorForDependencyBean, dependencyWrapper);
        context.setInjectionPoints(injectionPoints);
        context.getBeanDescriptors().add(descriptorForDependantBean);
        context.getBeanDescriptors().add(descriptorForDependencyBean);

        TestBean result = (TestBean) creator.createBeanWithDependencies(context, descriptorForDependantBean);

        assertNotEquals(dependencyBean, result.getDependency());
        assertNotEquals(testBean, result);
        assertEquals(result, context.getBeanContainer().getBean(descriptorForDependantBean));
        assertEquals(result.getDependency(), context.getBeanContainer().getBean(descriptorForDependencyBean));
    }

    @Test
    public void customBeanCreationAttemptPerformedTest() {
        BeanDescriptor descriptor = new BeanDescriptor("bean", TestBean.class, false);
        BeanCreator customCreator = mock(BeanCreator.class);
        TestBean testBean = new TestBean();
        testBean.setSomeIntField(42);
        testBean.setSomeStringField("test");
        when(customCreator.createBeanWithDependencies(context, descriptor)).thenReturn(testBean);
        ((CustomBeanCreator) creator).addCustomBeanCreator(customCreator);

        TestBean result = (TestBean) creator.createBeanWithDependencies(context, descriptor);

        assertEquals(testBean, result);
    }
}
