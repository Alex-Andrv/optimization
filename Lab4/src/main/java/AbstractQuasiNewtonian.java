import OneDimensionalOptimizationMethods.BrentMethod;

import java.util.function.Function;

public abstract class AbstractQuasiNewtonian implements OptimizationMethod {
    @Override
    public Vector findMin(MathFunction f, Vector start, double precision) {
        Vector pred = start;

        // start value
        Matrix G = Matrix.getIdentityMatrix(start.getRowCount());
        Vector w = f.grad(start).multiplyByConst(-1);
        final Vector p = w;
        double a = getAlpha(alpha -> f.evaluate(start.add(p.multiplyByConst(alpha))), precision);
        Vector next = start.add(p.multiplyByConst(a));

        while (next.sub(pred).norm() > precision) {
            Vector newW = f.grad(next).multiplyByConst(-1);
            Vector delX = next.sub(pred);
            Vector delW = newW.sub(w);
            G = getG(G, delX, delW);
            final Vector nextP = new Vector(G.multiply(newW));
            final Vector finalNext = next;
            a = getAlpha(alpha -> f.evaluate(finalNext.add(nextP.multiplyByConst(alpha))), precision);
            pred = next;
            w = newW;
            next = next.add(nextP.multiplyByConst(a));
        }
        return next;
    }

    private double getAlpha(Function<Double, Double> f, double precision) {
        return new BrentMethod(precision, -10, 10, f).findMin();
    }

    protected abstract Matrix getG(Matrix G, Vector delX, Vector delW);
}
