package net.study.tasks.model;

import net.study.tasks.annotation.Component;
import net.study.tasks.annotation.Inject;

@Component
public class Component5 extends BaseComponent {

    private LazyComponent2 lazyComponent2;
    private LazyComponent3 lazyComponent3;

    @Inject
    public Component5(LazyComponent2 lazyComponent2, LazyComponent3 lazyComponent3) {
        this.lazyComponent2 = lazyComponent2;
        this.lazyComponent3 = lazyComponent3;
    }

    @Override
    protected int doSomething(int a, int b) {
        return lazyComponent3.doSomething(a, b);
    }
}
