package net.study.tasks.model;

import net.study.tasks.annotation.Component;
import net.study.tasks.annotation.Inject;

@Component
public class Component3 extends BaseComponent {

    private LazyComponent4 lazyComponent4;
    private LazyComponent5 lazyComponent5;

    @Inject
    public void setLazyComponent4(LazyComponent4 lazyComponent4) {
        this.lazyComponent4 = lazyComponent4;
    }

    @Inject
    public void setLazyComponent5(LazyComponent5 lazyComponent5) {
        this.lazyComponent5 = lazyComponent5;
    }

    @Override
    protected int doSomething(int a, int b) {
        return lazyComponent4.doSomething(a, b);
    }
}
