package net.study.tasks.calculator;

import net.study.tasks.annotation.Component;
import net.study.tasks.annotation.Inject;

@Component
public class Calculator {

    private Adder adder;
    private Multiplier multiplier;

    @Inject
    public Calculator(Adder adder, Multiplier multiplier) {
        this.adder = adder;
        this.multiplier = multiplier;
    }

    public int add(int a, int b) {
        return adder.add(a, b);
    }

    public int multiply(int a, int b) {
        return multiplier.multiply(a, b);
    }
}
