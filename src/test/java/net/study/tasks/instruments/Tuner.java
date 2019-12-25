package net.study.tasks.instruments;

import net.study.tasks.annotation.Component;
import net.study.tasks.annotation.Inject;

@Component(lazy = true)
public class Tuner {

    // an empty constructor is required for cglib to work :(
    public Tuner() {
    }

    @Inject
    public Tuner(SomeLazyBean someLazyBean) {
        System.out.println("Lazy initialization started.");
        try {
            for(int i = 5; i > 0; i--) {
                System.out.println(i);
                Thread.sleep(1000);
            }
            System.out.println("Initialization completed.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tune(MusicalInstrument instrument) {
        if (instrument.isTuned()) {
            return;
        }
        instrument.setTuned(true);
    }
}
