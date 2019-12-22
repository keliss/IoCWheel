package net.study.tasks;

import net.study.tasks.annotation.Component;
import net.study.tasks.calculator.Calculator;

@Component
public class CalculationService {

    private Calculator calculator;

    public CalculationService(Calculator calculator) {
        this.calculator = calculator;
    }

    public int calculate(int a, int b) {
        if (a > 1) {
            return calculator.multiply(a, b);
        }
        return calculator.add(a, b);
    }
}
