public interface OptimizationMethod {
    Vector findMin(MathFunction f, Vector start, double precision);
}
