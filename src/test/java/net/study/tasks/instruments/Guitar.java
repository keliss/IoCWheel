package net.study.tasks.instruments;

import net.study.tasks.annotation.Component;
import net.study.tasks.annotation.Inject;

@Component
public class Guitar extends MusicalInstrument {

    @Inject
    private Mediator mediator;

    @Override
    public void play() {
        System.out.println();
        System.out.println("<-------------------------------------------------------------------------->");
        System.out.println("|*a joyful guitar melody fills up the air, telling that DI works just fine*|");
        System.out.println("<-------------------------------------------------------------------------->");
    }

    @Override
    public void repair() {
        setState(100);
    }
}
