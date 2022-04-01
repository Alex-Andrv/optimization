import java.util.function.Function;

public class NewtonMethod implements OptimizationMethod {
    public Vector findMin(MathFunction f, Vector start, double precision) {
        Vector prev = start;
        while (true) {
            Vector antiGrad = f.grad(prev).multiplyByConst(-1);
            Matrix h = f.hessian(prev);
            Vector p = getP(ModifiedGaussMethod.solve(h, antiGrad), antiGrad);
            Vector finalPrev = prev;
            prev = prev.add(p.multiplyByConst(getAlpha(alpha -> f.evaluate(finalPrev.add(p.multiplyByConst(alpha))), precision)));
            if (p.norm() < precision) {
                break;
            }
        }
        return prev;
    }

    protected Vector getP(Vector p, Vector antiGrad) {
        return p;
    }


    protected double getAlpha(Function<Double, Double> f, double precision) {
        return 1.0;
    }
}
