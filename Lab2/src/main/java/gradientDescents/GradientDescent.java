package gradientDescents;

import optimizationMethods.GoldenRatioMethod;
import optimizationMethods.OptimizationMethod;

import java.util.function.Function;

public class GradientDescent {
    private final Function<Vector, Double> func;
    private final Function<Vector, Vector> grad;
    private final Vector start;
    private final double precision;
    private double a;
    private static final int MAX_ITERATIONS = 30000;
    public int counter = 0;


    public GradientDescent(Function<Vector, Double> func, Function<Vector,
            Vector> grad, Vector start, double precision, double a) {
        this.func = func;
        this.grad = grad;
        this.start = start;
        this.precision = precision;
        this.a = a;
    }

    public GradientDescent(QuadraticFunction quadraticFunction, Vector start, double precision, double a) {
        this(quadraticFunction::evaluate, quadraticFunction::grad, start, precision, a);
    }

    public Vector findMin() {
        Vector cur = start;
        int iterCounter = 0;
        Vector currGrad = grad.apply(cur);
        double currFunc = func.apply(cur);
        while (iterCounter < MAX_ITERATIONS && currGrad.norm() > precision) {
            Vector next = cur.sub(currGrad.multiplyByConst(a));
            double nextFunc = func.apply(next);
            while (currFunc < nextFunc) {
                a = a / 2;
                next = cur.sub(currGrad.multiplyByConst(a));
                nextFunc = func.apply(next);
            }
            cur = next;
            currGrad = grad.apply(cur);
            currFunc = nextFunc;
            iterCounter++;

        }
        counter = iterCounter;
        return cur;
    }

}
