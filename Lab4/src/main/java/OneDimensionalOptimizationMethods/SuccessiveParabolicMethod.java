package OneDimensionalOptimizationMethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class SuccessiveParabolicMethod extends AbstractMethod implements ParabolicMethod {

    private final List<Parabola> intermediateParabolas = new ArrayList<>();

    @Override
    public List<Parabola> getIntermediateParabolas() {
        return Collections.unmodifiableList(intermediateParabolas);
    }

    public SuccessiveParabolicMethod(double eps, double start, double end, Function<Double, Double> f) {
        super(eps, start, end, f);
    }

    /**
     * This find minimum of a parabola defined by three points: left, middle, right
     */
    public static double findMinDot(Point left, Point middle, Point right) {
        double a1 = (middle.getY() - left.getY()) / (middle.getX() - left.getX());
        double a2 = ((right.getY() - left.getY()) / (right.getX() - left.getX()) - a1) / (right.getX() - middle.getX());
        return (left.getX() + middle.getX() - a1 / a2) / 2;
    }

    /**
     * recount new range
     */
    public static void recountPoint(Point left, Point middle, Point right, Point minDot) {
        if (minDot.compareToX(middle) == 0)
            return;
        if (minDot.compareToX(left) >= 0 && middle.compareToX(minDot) >= 0) {
            if (minDot.compareToY(middle) <= 0) {
                right.set(middle);
                middle.set(minDot);
            } else {
                left.set(minDot);
            }
        } else {
            if (minDot.compareToY(middle) <= 0) {
                left.set(middle);
                middle.set(minDot);
            } else {
                right.set(minDot);
            }
        }
    }

    /**
     * Find random Point middle in range(left.x, right.x), that satisfies
     * condition: f(left.x) < f(middle) < f(right.x)
     */
    private Point start_value(Point left, Point right) { // find random value in range(left.x, right.t)
        Point middle = new Point(left.getX() + (right.getX() - left.getX()) * Math.random());
        while (!(middle.compareToY(left) < 0 && middle.compareToY(right) < 0)) {
            middle.setX(left.getX() + (Math.random() * (right.getX() - left.getX())));
        }
        return middle;
    }

    /**
     * finding the minimum of a unimodal function
     */
    @Override
    public double findMin() {
        Point left = new Point(start);
        Point right = new Point(end);
        Point middle = start_value(left, right);
        Point predMin = new Point(Math.max(start, end));

        intermediateSegments.clear();
        intermediateSegments.add(new Segment(left.getX(), right.getX()));

        intermediateParabolas.clear();
        intermediateParabolas.add(new Parabola(left, middle, right));

        while (true) {
            Point minDot = new Point(findMinDot(left, middle, right));
            if (minDot.compareToX(predMin) == 0) {
                return minDot.getX();
            }
            recountPoint(left, middle, right, minDot);
            predMin.set(minDot);

            intermediateSegments.add(new Segment(left.getX(), right.getX()));

            intermediateParabolas.add(new Parabola(left, middle, right));
        }
    }
}
