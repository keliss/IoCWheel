package infrastructure.scanner;

import java.util.ArrayList;
import java.util.List;

public class DelegatingAnnotationScanner implements AnnotationScanner {

    public List<AnnotationScanner> annotationScanners;

    public DelegatingAnnotationScanner() {
        annotationScanners = new ArrayList<>();
        annotationScanners.add(new InjectAnnotationScanner());
        annotationScanners.add(new ComponentAnnotationScanner());
    }

    @Override
    public void scan() {
        annotationScanners.forEach(AnnotationScanner::scan);
    }
}
