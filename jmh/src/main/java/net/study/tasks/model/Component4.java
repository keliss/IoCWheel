package net.study.tasks.model;

import net.study.tasks.annotation.Component;
import net.study.tasks.annotation.Inject;

@Component
public class Component4 extends BaseComponent {

    private Component5 component5;

    @Inject
    public Component4(Component5 component5) {
        this.component5 = component5;
    }

    @Override
    protected int doSomething(int a, int b) {
        return component5.doSomething(a, b);
    }
}
