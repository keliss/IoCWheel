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
        try {
            Thread.sleep(5000);
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
