public interface MathFunction {
    Vector grad(Vector x);
    double evaluate(Vector x);
    Matrix hessian(Vector x);
}
