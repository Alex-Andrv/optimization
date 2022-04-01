public class Test {
    public static void test() {
        Matrix a = new Matrix(new double[][]{
                {6, -5},
                {-5, 6}});
        Vector b = new Vector(-7, 2);
        double c = -30;
        Vector start = new Vector(1, 1);
        QuadraticFunction qf = new QuadraticFunction(a, b, c);

        FirstFunction first = new FirstFunction();
        System.out.println("--------First function----------");
        System.out.println("BFGS: " + first.evaluate(new BFGS().findMin(first, start, 1e-4)));
        System.out.println("Powell: " + first.evaluate(new PowellMethod().findMin(first, start, 1e-4)));
        System.out.println("Newton: " + first.evaluate(new DirectionalNewton().findMin(first, start, 1e-4)));

        SecondFunction second = new SecondFunction();
        System.out.println("--------Second function----------");
        System.out.println("BFGS: " + second.evaluate(new BFGS().findMin(second, start, 1e-4)));
        System.out.println("Powell: " + second.evaluate(new PowellMethod().findMin(second, start, 1e-4)));
        System.out.println("Newton: " + second.evaluate(new DirectionalNewton().findMin(second, start, 1e-4)));

        ThirdFunction third = new ThirdFunction();
        Vector thirdStart = new Vector(1, 1, 1, 1);
        System.out.println("--------Third function----------");
        System.out.println("BFGS: " + third.evaluate(new BFGS().findMin(third, thirdStart, 1e-4)));
        System.out.println("Powell: " + third.evaluate(new PowellMethod().findMin(third, thirdStart, 1e-4)));
        System.out.println("Newton: " + third.evaluate(new DirectionalNewton().findMin(third, thirdStart, 1e-4)));

        FourthFunction fourth = new FourthFunction();
        System.out.println("--------Fourth function----------");
        System.out.println("BFGS: " + fourth.evaluate(new BFGS().findMin(fourth, start, 1e-4)));
        System.out.println("Powell: " + fourth.evaluate(new PowellMethod().findMin(fourth, start, 1e-4)));
        System.out.println("Newton: " + fourth.evaluate(new DirectionalNewton().findMin(fourth, start, 1e-4)));
    }

    public static void main(String[] args) {
        test();
    }

    public static class FirstFunction implements MathFunction {

        @Override
        public Vector grad(Vector x) {
            return new Vector(
                    2 * (200 * x.get(0) * x.get(0) * x.get(0) - 200 * x.get(0) * x.get(1) + x.get(0) - 1),
                    200 * (x.get(1) - x.get(0) * x.get(0))
            );
        }

        @Override
        public double evaluate(Vector x) {
            return 100 * (x.get(1) - x.get(0) * x.get(0)) * (x.get(1) - x.get(0) * x.get(0))
                    + (1 - x.get(0)) * (1 - x.get(0));
        }

        @Override
        public Matrix hessian(Vector x) {
            return new Matrix(new double[][]{
                    {-400 * (x.get(1) - x.get(0) * x.get(0)) + 800 * x.get(0) * x.get(0) + 2, -400 * x.get(0)},
                    {-400 * x.get(0), 200}
            });
        }
    }

    public static class SecondFunction implements MathFunction {

        @Override
        public Vector grad(Vector x) {
            return new Vector(
                    2 * (2 * x.get(0) * (x.get(0) * x.get(0) + x.get(1) - 11) + x.get(0) + x.get(1) * x.get(1) - 7),
                    2 * (x.get(0) * x.get(0) + 2 * x.get(1) * (x.get(0) + x.get(1) * x.get(1) - 7) + x.get(1) - 11));
        }

        @Override
        public double evaluate(Vector x) {
            return (x.get(0) * x.get(0) + x.get(1) - 11) * (x.get(0) * x.get(0) + x.get(1) - 11)
                    + (x.get(0) + x.get(1) * x.get(1) - 7) * (x.get(0) + x.get(1) * x.get(1) - 7);
        }

        @Override
        public Matrix hessian(Vector x) {
            return new Matrix(new double[][]{
                    {4 * (x.get(0) * x.get(0) + x.get(1) - 11) + 8 * x.get(0) * x.get(0) + 2, 4 * x.get(0) + 4 * x.get(1)},
                    {4 * x.get(0) + 4 * x.get(1), 4 * (x.get(0) + x.get(1) * x.get(1) - 7) + 8 * x.get(1) * x.get(1) + 2}
            });
        }
    }

    public static class ThirdFunction implements MathFunction {

        @Override
        public Vector grad(Vector x) {
            return new Vector(
                    2 * (20 * (x.get(0) - x.get(3)) * (x.get(0) - x.get(3)) * (x.get(0) - x.get(3)) + x.get(0) + 10 * x.get(1)),
                    4 * (5 * (x.get(0) + 10 * x.get(1)) + (x.get(1) - 2 * x.get(2)) * (x.get(1) - 2 * x.get(2)) * (x.get(1) - 2 * x.get(2))),
                    10 * (x.get(2) - x.get(3)) - 8 * (x.get(1) - 2 * x.get(2)) * (x.get(1) - 2 * x.get(2)) * (x.get(1) - 2 * x.get(2)),
                    10 * (-4 * (x.get(0) - x.get(3)) * (x.get(0) - x.get(3)) * (x.get(0) - x.get(3)) + x.get(3) - x.get(2))
            );
        }

        @Override
        public double evaluate(Vector x) {
            return (x.get(0) + 10 * x.get(1)) * (x.get(0) + 10 * x.get(1))
                    + 5 * (x.get(2) - x.get(3)) * (x.get(2) - x.get(3))
                    + (x.get(1) - 2 * x.get(2)) * (x.get(1) - 2 * x.get(2)) * (x.get(1) - 2 * x.get(2)) * (x.get(1) - 2 * x.get(2))
                    + 10 * (x.get(0) - x.get(3)) * (x.get(0) - x.get(3)) * (x.get(0) - x.get(3)) * (x.get(0) - x.get(3));
        }

        @Override
        public Matrix hessian(Vector x) {
            return new Matrix(new double[][]{
                    {120 * (x.get(0) - x.get(3)) * (x.get(0) - x.get(3)) + 2, 20, 0, -120 * (x.get(0) - x.get(3)) * (x.get(0) - x.get(3))},
                    {20, 12 * (x.get(1) - 2 * x.get(2)) * (x.get(1) - 2 * x.get(2)) + 200, -24 * (x.get(1) - 2 * x.get(2)) * (x.get(1) - 2 * x.get(2)), 0},
                    {0, -24 * (x.get(1) - 2 * x.get(2)) * (x.get(1) - 2 * x.get(2)), 48 * (x.get(1) - 2 * x.get(2)) * (x.get(1) - 2 * x.get(2)) + 10, -10},
                    {-120 * (x.get(0) - x.get(3)) * (x.get(0) - x.get(3)), 0, -10, 120 * (x.get(0) - x.get(3)) * (x.get(0) - x.get(3)) + 10}
            });
        }
    }

    public static class FourthFunction implements MathFunction {

        @Override
        public Vector grad(Vector x) {
            return new Vector(
                    648 * (x.get(0) - 2) / ((9 * x.get(0) * x.get(0) - 36 * x.get(0) + 4 * x.get(1) * x.get(1) - 8 * x.get(1) + 76)
                            * (9 * x.get(0) * x.get(0) - 36 * x.get(0) + 4 * x.get(1) * x.get(1) - 8 * x.get(1) + 76))
                            + (x.get(0) - 1) / (((x.get(0) - 1) * (x.get(0) - 1) / 4 + (x.get(1) - 1) * (x.get(1) - 1) / 9 + 1)
                            * ((x.get(0) - 1) * (x.get(0) - 1) / 4 + (x.get(1) - 1) * (x.get(1) - 1) / 9 + 1)),
                    2 * (x.get(1) - 1) / 9 *
                            (2 / ((1 + ((x.get(0) - 1) / 2) * ((x.get(0) - 1) / 2) + ((x.get(1) - 1) / 3) * ((x.get(1) - 1) / 3))
                                    * (1 + ((x.get(0) - 1) / 2) * ((x.get(0) - 1) / 2) + ((x.get(1) - 1) / 3) * ((x.get(1) - 1) / 3)))
                                    + 1 / ((1 + ((x.get(0) - 2) / 2) * ((x.get(0) - 2) / 2) + ((x.get(1) - 1) / 3) * ((x.get(1) - 1) / 3)) *
                                    (1 + ((x.get(0) - 2) / 2) * ((x.get(0) - 2) / 2) + ((x.get(1) - 1) / 3) * ((x.get(1) - 1) / 3))))
            );
        }

        @Override
        public double evaluate(Vector x) {
            return 100
                    - 2 / (1 + ((x.get(0) - 1) / 2) * ((x.get(0) - 1) / 2) + ((x.get(1) - 1) / 3) * ((x.get(1) - 1) / 3))
                    - 1 / (1 + ((x.get(0) - 2) / 2) * ((x.get(0) - 2) / 2) + ((x.get(1) - 1) / 3) * ((x.get(1) - 1) / 3));
        }

        @Override
        public Matrix hessian(Vector point) {
            double x = point.get(0);
            double y = point.get(1);
            return new Matrix(new double[][]{
                    {-(-2 + x) * (-2 + x) / (2 * (1 + (-2 + x) * (-2 + x) / 4 + (-1 + y) * (-1 + y) / 9) * (1 + (-2 + x) * (-2 + x) / 4 + (-1 + y) * (-1 + y) / 9) * (1 + (-2 + x) * (-2 + x) / 4 + (-1 + y) * (-1 + y) / 9)) + 1 / (2 * (1 + (-2 + x) * (-2 + x) / 4 + (-1 + y) * (-1 + y) / 9) * (1 + (-2 + x) * (-2 + x) / 4 + (-1 + y) * (-1 + y) / 9)) - (-1 + x) * (-1 + x) / (1 + (-1 + x) * (-1 + x) / 4 + (-1 + y) * (-1 + y) / 9) * (1 + (-1 + x) * (-1 + x) / 4 + (-1 + y) * (-1 + y) / 9) * (1 + (-1 + x) * (-1 + x) / 4 + (-1 + y) * (-1 + y) / 9) + 1 / ((1 + (-1 + x) * (-1 + x) / 4 + (-1 + y) * (-1 + y) / 9) * (1 + (-1 + x) * (-1 + x) / 4 + (-1 + y) * (-1 + y) / 9)),
                            (-2 * (-2 + x) * (-1 + y)) / (9 * (1 + (-2 + x) * (-2 + x) / 4 + (-1 + y) * (-1 + y) / 9) * (1 + (-2 + x) * (-2 + x) / 4 + (-1 + y) * (-1 + y) / 9) * (1 + (-2 + x) * (-2 + x) / 4 + (-1 + y) * (-1 + y) / 9)) - (4 * (-1 + x) * (-1 + y)) / (9 * (1 + (-1 + x) * (-1 + x) / 4 + (-1 + y) * (-1 + y) / 9) * (1 + (-1 + x) * (-1 + x) / 4 + (-1 + y) * (-1 + y) / 9) * (1 + (-1 + x) * (-1 + x) / 4 + (-1 + y) * (-1 + y) / 9))},
                    {(-2 * (-2 + x) * (-1 + y)) / (9 * (1 + (-2 + x) * (-2 + x) / 4 + (-1 + y) * (-1 + y) / 9) * (1 + (-2 + x) * (-2 + x) / 4 + (-1 + y) * (-1 + y) / 9) * (1 + (-2 + x) * (-2 + x) / 4 + (-1 + y) * (-1 + y) / 9)) - (4 * (-1 + x) * (-1 + y)) / (9 * (1 + (-1 + x) * (-1 + x) / 4 + (-1 + y) * (-1 + y) / 9) * (1 + (-1 + x) * (-1 + x) / 4 + (-1 + y) * (-1 + y) / 9) * (1 + (-1 + x) * (-1 + x) / 4 + (-1 + y) * (-1 + y) / 9)),
                            2 / (9 * (1 + (-2 + x) * (-2 + x) / 4 + (-1 + y) * (-1 + y) / 9) * (1 + (-2 + x) * (-2 + x) / 4 + (-1 + y) * (-1 + y) / 9)) + 4 / (9 * (1 + (-1 + x) * (-1 + x) / 4 + (-1 + y) * (-1 + y) / 9) * (1 + (-1 + x) * (-1 + x) / 4 + (-1 + y) * (-1 + y) / 9)) - (8 * (-1 + y) * (-1 + y)) / (81 * (1 + (-2 + x) * (-2 + x) / 4 + (-1 + y) * (-1 + y) / 9) * (1 + (-2 + x) * (-2 + x) / 4 + (-1 + y) * (-1 + y) / 9) * (1 + (-2 + x) * (-2 + x) / 4 + (-1 + y) * (-1 + y) / 9)) - (16 * (-1 + y) * (-1 + y)) / (81 * (1 + (-1 + x) * (-1 + x) / 4 + (-1 + y) * (-1 + y) / 9) * (1 + (-1 + x) * (-1 + x) / 4 + (-1 + y) * (-1 + y) / 9) * (1 + (-1 + x) * (-1 + x) / 4 + (-1 + y) * (-1 + y) / 9))}
            });
        }
    }
}
