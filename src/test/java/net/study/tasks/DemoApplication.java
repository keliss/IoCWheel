package net.study.tasks;

import net.study.tasks.annotation.Component;
import net.study.tasks.annotation.Inject;
import net.study.tasks.infrastructure.ApplicationEntryPoint;
import net.study.tasks.infrastructure.Runner;

@Component
public class DemoApplication implements Runner {

    @Inject
    private CalculationService service;

    public static void main(String[] args) {
        ApplicationEntryPoint.launch(DemoApplication.class);

    }

    @Override
    public void run() {
        System.out.println(service.calculate(5, 4));
    }
}
