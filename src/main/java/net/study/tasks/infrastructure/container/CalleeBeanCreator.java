package net.study.tasks.infrastructure.container;

public interface CalleeBeanCreator extends BeanCreator {

    Object callback();

    BeanCreator getCallback();

    void setCallback(BeanCreator callback);
}
