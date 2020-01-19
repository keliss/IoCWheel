package net.study.tasks.model;

import net.study.tasks.annotation.Component;
import net.study.tasks.annotation.Inject;

@Component
public class Component2 extends BaseComponent {

    @Inject
    private Component4 component4;
    @Inject
    private Component5 component5;
    private LazyComponent3 lazyComponent3;

    @Inject
    public void setLazyComponent3(LazyComponent3 lazyComponent3) {
        this.lazyComponent3 = lazyComponent3;
    }

    public void setComponent4(Component4 component4) {
        this.component4 = component4;
    }

    public void setComponent5(Component5 component5) {
        this.component5 = component5;
    }

    @Override
    protected int doSomething(int a, int b) {
        return component4.doSomething(a, b) + component5.doSomething(a, b) + lazyComponent3.doSomething(a, b);
    }
}
