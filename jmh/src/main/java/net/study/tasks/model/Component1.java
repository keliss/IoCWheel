package net.study.tasks.model;

import net.study.tasks.annotation.Component;
import net.study.tasks.annotation.Inject;

@Component
public class Component1 extends BaseComponent {

    private Component2 component2;
    private Component3 component3;
    private LazyComponent1 lazyComponent1;
    private LazyComponent2 lazyComponent2;
    private int state;

    @Inject
    public Component1(Component2 component2, Component3 component3, LazyComponent1 lazyComponent1, LazyComponent2 lazyComponent2) {
        this.component2 = component2;
        this.component3 = component3;
        this.lazyComponent1 = lazyComponent1;
        this.lazyComponent2 = lazyComponent2;
    }

    @Override
    protected int doSomething(int a, int b) {
        return component2.doSomething(a, b) + lazyComponent1.doSomething(a, b) + component3.doSomething(a, b);
    }

    public void setState(int state) {
        this.state = state;
    }
}
