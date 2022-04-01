import java.util.function.Function;

public class ConjugateGradientMethod implements Solver {

    private final double precision;
    private int cnt;

    ConjugateGradientMethod(double precision) {
        this.precision = precision;
    }

    public int getCnt() {
        return cnt;
    }


    public Vector solve(Matrix a, Vector b) {
        cnt = 0;
        b = b.multiplyByConst(-1);
        Vector curr = new Vector(b.size());
        Vector currGrad = a.multiplicationForVector(curr).add(b);
        double currGradNormSqr = currGrad.dotProduct(currGrad);
        Vector p = currGrad.multiplyByConst(-1);
        while (currGradNormSqr > precision) {
            cnt++;
            Vector aP = a.multiplicationForVector(p);
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
