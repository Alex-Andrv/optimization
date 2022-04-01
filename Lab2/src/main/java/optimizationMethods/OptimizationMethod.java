package optimizationMethods;

import java.util.List;

public interface OptimizationMethod {
    double findMin();
    List<Segment> getIntermediateSegments();
}
