package methods;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractMethod implements OptimizationMethod {
    private static final double COMPARE_PRECISION = 1e-10;
    protected final double eps;
    protected final double start;
    protected final double end;
    protected final String name;
    protected final Function<Double, Double> f;
    protected final List<Segment> intermediateSegments = new ArrayList<>();

    protected AbstractMethod(String name, double eps, double start, double end, Function<Double, Double> f) {
        this.name = name;
        this.eps = eps;
        this.start = start;
        this.end = end;
        this.f = f;
    }

    double lenOX(Point a, Point b) {
        return Math.abs(a.getX() - b.getX());
    }

    double lenOX(double a, double b) {
        return Math.abs(a - b);
    }

    public String toString() {
        return name + " at " + "[" + start + ":" + end + "]" + " with exp = " + eps;
    }

    protected int compare(double x, double y) {
        if (x + COMPARE_PRECISION < y) {
            return -1;
        }
        if (x > y + COMPARE_PRECISION) {
            return 1;
        }
        return 0;
    }

    public abstract double findMin();

    public class Point implements Comparable<Point> {
        private double epsY = 1e-9;
        private double x;
        private double y;

        Point(double x) {
            this.x = x;
            this.y = f.apply(x);
        }

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        Point(Point point) {
            this.x = point.getX();
            this.y = point.getY();
        }

        public String toString() {
            return String.format("(%.4f:%.4f)", x, y);
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
            this.y = f.apply(x);
        }

        public void set(Point p) {
            this.x = p.getX();
            this.y = p.getY();
        }


        public double getY() {
            return y;
        }

        public int compareToX(Point o) {
            return Math.abs(o.getX() - this.x) < eps ? 0 : (this.x > o.getX() ? 1 : -1);
        }

        public int compareToY(Point o) {
            return Math.abs(o.getY() - this.y) < epsY ? 0 : (this.y > o.getY() ? 1 : -1);
        }

        @Override
        public int compareTo(Point o) {
            return compareToX(o);
        }
    }

    @Override
    public List<Segment> getIntermediateSegments() {
        return intermediateSegments;
    }
}
