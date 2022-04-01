import OneDimensionalOptimizationMethods.BrentMethod;

import java.util.function.Function;

public class NewtonWithOneDimensionalSearch extends NewtonMethod {

    @Override
    protected double getAlpha(Function<Double, Double> f, double precision) {
        return new BrentMethod(precision, 0, 10, f).findMin();
    }
}
