package net.study.tasks.infrastructure;

import net.study.tasks.infrastructure.pipeline.ApplicationPipeline;

public class ApplicationEntryPoint {

    public static void launch(Class<?> basePackageScanClass) {
        ApplicationContext.getInstance().setBasePackageScanClass(basePackageScanClass);
        new ApplicationPipeline().launch();
    }
}
