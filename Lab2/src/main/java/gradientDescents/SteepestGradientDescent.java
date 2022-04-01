package gradientDescents;

import optimizationMethods.BrentMethod;
import optimizationMethods.GoldenRatioMethod;
import optimizationMethods.OptimizationMethod;

import java.util.function.Function;

public class SteepestGradientDescent {
    private final Function<Vector, Double> func;
    private final Function<Vector, Vector> grad;
    private final Vector start;
    private final double precision;
    private static final int MAX_ITERATIONS = 30000;
    public int counter = 0;

    public SteepestGradientDescent(Function<Vector, Double> func, Function<Vector, Vector> grad, Vector start, double precision) {
        this.func = func;
        this.grad = grad;
        this.start = start;
        this.precision = precision;
    }

    public SteepestGradientDescent(QuadraticFunction quadraticFunction, Vector start, double precision) {
        this(quadraticFunction::evaluate, quadraticFunction::grad, start, precision);
    }

    public Vector findMin() {
        Vector prev = start;
        Vector cur = prev;
        int iterCounter = 0;
        do {
            final Vector finalCur = cur;
            OptimizationMethod method = new BrentMethod(1e-4, 0, 1e4,
                    x -> func.apply(finalCur.sub(grad.apply(finalCur).multiplyByConst(x))));
            Vector next = cur.sub(grad.apply(cur).multiplyByConst(method.findMin()));
            prev = cur;
            cur = next;
            iterCounter++;
        } while (prev.sub(cur).norm() > precision && iterCounter < MAX_ITERATIONS);
        counter = iterCounter;
        return cur;
    }
}
