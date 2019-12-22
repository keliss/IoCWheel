package infrastructure.pipeline;

import infrastructure.scanner.DelegatingAnnotationScanner;
import infrastructure.scanner.AnnotationScanner;

public class AnnotationScanStep implements PipelineStep {

    private AnnotationScanner annotationScanner;

    public AnnotationScanStep(AnnotationScanner annotationScanner) {
        this.annotationScanner = annotationScanner;
    }

    public AnnotationScanStep() {
        this(new DelegatingAnnotationScanner());
    }

    @Override
    public void apply() {
        annotationScanner.scan();
    }
}
