package net.study.tasks.model;

import net.study.tasks.annotation.Component;
import net.study.tasks.annotation.Inject;

@Component(lazy = true)
public class LazyComponent1 extends BaseComponent {

    @Inject
    private LazyComponent5 lazyComponent5;

    @Override
    protected int doSomething(int a, int b) {
        return lazyComponent5.doSomething(a, b);
    }

    public void setLazyComponent5(LazyComponent5 lazyComponent5) {
        this.lazyComponent5 = lazyComponent5;
    }
}
