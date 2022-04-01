public class DirectionalNewton extends NewtonWithOneDimensionalSearch {

    @Override
    protected Vector getP(Vector p, Vector antiGrad) {
        if (p.dotProduct(antiGrad) > 0) {
            return p;
        } else {
            return antiGrad;
        }
    }
}
