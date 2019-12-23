package net.study.tasks;

import net.study.tasks.annotation.Component;
import net.study.tasks.annotation.Inject;
import net.study.tasks.infrastructure.ApplicationEntryPoint;
import net.study.tasks.infrastructure.Runner;

@Component
public class DemoApplication implements Runner {

    private Musician musician;

    public static void main(String[] args) {
        ApplicationEntryPoint.launch(DemoApplication.class);
    }

    @Inject
    public void hireMusician(Musician musician) {
        this.musician = musician;
    }

    @Override
    public void run() {
        musician.perform();
    }
}
