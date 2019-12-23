package net.study.tasks.infrastructure.pipeline;

import net.study.tasks.infrastructure.ApplicationContext;
import net.study.tasks.infrastructure.Runner;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Set;

public class RunnerExecutionStep implements PipelineStep {

    @Override
    public void apply() {
        Class<?> basePackageScanClass = ApplicationContext.getBasePackageScanClass();
        Set<Class<? extends Runner>> runnerClasses = new Reflections(basePackageScanClass).getSubTypesOf(Runner.class);
        runnerClasses.forEach(c -> {
            try {
                Method runMethod = c.getMethod("run");
                runMethod.setAccessible(true);
                runMethod.invoke(ApplicationContext.getBeanContainer().getBeansByClass(c).get(0).getValue());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
