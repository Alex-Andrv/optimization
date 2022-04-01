public class QuadraticFunction {
    private final Matrix matrixA;
    private final Vector vectorB;
    private final double c;

    /**
     * Constructs quadratic function: {@code f(x) = 0.5 * <Ax, x> + <b, x> + c}.
     * Given {@code matrixA} should be symmetric with doubled main diagonal.
     *
     * @param matrixA given A
     * @param vectorB given b
     * @param c       given c
     */
    public QuadraticFunction(Matrix matrixA, Vector vectorB, double c) {
        this.matrixA = matrixA;
        this.vectorB = vectorB;
        this.c = c;
    }

    public Vector grad(Vector x) {
        return matrixA.multiplicationForVector(x).add(vectorB);
    }

    public double evaluate(Vector x) {
        return matrixA.multiplicationForVector(x).dotProduct(x) * 0.5 + vectorB.dotProduct(x) + c;
    }

    public Matrix getMatrixA() {
        return matrixA;
    }
}
