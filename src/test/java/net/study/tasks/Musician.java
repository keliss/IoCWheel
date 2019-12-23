package net.study.tasks;

import net.study.tasks.annotation.Component;
import net.study.tasks.annotation.Inject;
import net.study.tasks.instruments.MusicalInstrument;
import net.study.tasks.instruments.Tuner;

@Component
public class Musician {

    @Inject
    private Tuner tuner;

    private MusicalInstrument instrument;
    private int salary;

    @Inject
    public Musician(MusicalInstrument instrument) {
        this.instrument = instrument;
    }

    public void perform() {
        tuner.tune(instrument);
        instrument.play();
    }

    public MusicalInstrument getInstrument() {
        return instrument;
    }

    public void setInstrument(MusicalInstrument instrument) {
        this.instrument = instrument;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
