package OneDimensionalOptimizationMethods;

import java.util.List;

public interface OneDimensionalOptimizationMethod {
    double findMin();
    List<Segment> getIntermediateSegments();
}
