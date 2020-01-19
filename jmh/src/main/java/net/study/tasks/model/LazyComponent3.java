package net.study.tasks.model;

import net.study.tasks.annotation.Component;

@Component(lazy = true)
public class LazyComponent3 extends BaseComponent {

    @Override
    protected int doSomething(int a, int b) {
        return 44 + 2;
    }
}
