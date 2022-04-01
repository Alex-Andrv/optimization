package gradientDescents;

import java.util.function.Function;

public class ConjugateGradientMethod {
    private final Function<Vector, Double> func;
    private final Function<Vector, Vector> grad;
    private final Vector start;
    private final double precision;
    private final Matrix A;
    public int counter = 0;

    public ConjugateGradientMethod(Function<Vector, Double> func, Matrix A, Function<Vector, Vector> grad, Vector start, double precision) {
        this.func = func;
        this.grad = grad;
        this.start = start;
        this.precision = precision;
        this.A = A;
    }

    public ConjugateGradientMethod(QuadraticFunction quadraticFunction, Vector start, double precision) {
        this(quadraticFunction::evaluate, quadraticFunction.getMatrixA(), quadraticFunction::grad, start, precision);
    }

    public Vector findMin() {
        Vector curr = start;
        Vector currGrad = grad.apply(curr);
        double currGradNormSqr = currGrad.dotProduct(currGrad);
        Vector p = currGrad.multiplyByConst(-1);
        while (currGradNormSqr > precision) {
            counter++;
            Vector aP = new Vector(A.multiply(p));
            double alpha = currGradNormSqr / aP.dotProduct(p);
            curr = curr.add(p.multiplyByConst(alpha));
            currGrad = currGrad.add(aP.multiplyByConst(alpha));
            double prevGradNormSqr = currGradNormSqr;
            currGradNormSqr = currGrad.dotProduct(currGrad);
            double beta = currGradNormSqr / prevGradNormSqr;
            p = currGrad.multiplyByConst(-1).add(p.multiplyByConst(beta));
        }
        return curr;
    }
}
