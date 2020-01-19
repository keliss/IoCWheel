package net.study.tasks.model;

import net.study.tasks.annotation.Component;
import net.study.tasks.annotation.Inject;
import net.study.tasks.infrastructure.Runner;

import java.util.Random;

@Component
public class EntryPoint implements Runner {

    @Inject
    private Component1 component1;

    private int result;

    @Override
    public void run() {
        Random random = new Random();
        result = component1.doSomething(random.nextInt(), random.nextInt());
        component1.setState(result);
    }

    public void setComponent1(Component1 component1) {
        this.component1 = component1;
    }
}
