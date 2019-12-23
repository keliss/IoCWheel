package net.study.tasks.instruments;

import net.study.tasks.annotation.Component;

@Component
public class Tuner {

    public void tune(MusicalInstrument instrument) {
        if (instrument.isTuned()) {
            return;
        }
        instrument.setTuned(true);
    }
}
