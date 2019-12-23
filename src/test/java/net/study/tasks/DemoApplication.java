package net.study.tasks;

import net.study.tasks.annotation.Component;
import net.study.tasks.annotation.Inject;
import net.study.tasks.infrastructure.ApplicationEntryPoint;
import net.study.tasks.infrastructure.Runner;

// This dumb application shows the features existing at the moment: injection into a field,
// injection into a setter and injection into a constructor

@Component
public class DemoApplication implements Runner {

    private Musician musician;

    public static void main(String[] args) {
        ApplicationEntryPoint.launch(DemoApplication.class);
    }

    @Inject
    public void hireMusician(Musician musician) {
        this.musician = musician;
        musician.setSalary(5000);
    }

    @Override
    public void run() {
        musician.perform();
    }
}
