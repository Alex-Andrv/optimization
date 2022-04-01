package OneDimensionalOptimizationMethods;

public class Parabola {
    // y=ax^2+bx+c
    private final double a;
    private final double b;
    private final double c;

    public Parabola(AbstractMethod.Point p1, AbstractMethod.Point p2, AbstractMethod.Point p3) {
        this.a = (p3.getY() - (p3.getX() * (p2.getY() - p1.getY()) + p2.getX() * p1.getY() - p1.getX() * p2.getY()) / (p2.getX() - p1.getX())) / (p3.getX() * (p3.getX() - p1.getX() - p2.getX()) + p1.getX() * p2.getX());
        this.b = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX()) - a * (p1.getX() + p2.getX());
        this.c = (p2.getX() * p1.getY() - p1.getX() * p2.getY()) / (p2.getX() - p1.getX()) + a * p1.getX() * p2.getX();
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }
}
