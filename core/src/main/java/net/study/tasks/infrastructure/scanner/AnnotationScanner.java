package net.study.tasks.infrastructure.scanner;

import net.study.tasks.infrastructure.ApplicationContext;

public interface AnnotationScanner<T> {

    T scan(ApplicationContext context);
}
