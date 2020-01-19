package net.study.tasks.model;

import net.study.tasks.annotation.Component;
import net.study.tasks.annotation.Inject;

@Component(lazy = true)
public class LazyComponent4 extends BaseComponent {

    private LazyComponent5 lazyComponent5;

    public LazyComponent4() {
    }

    @Inject
    public LazyComponent4(LazyComponent5 lazyComponent5) {
        this.lazyComponent5 = lazyComponent5;
    }
}
