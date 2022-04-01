package OneDimensionalOptimizationMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BrentMethod extends AbstractMethod {

    private static final double INV_GOLDEN_RATIO = (Math.sqrt(5) - 1) / 2;


    public BrentMethod(double eps, double start, double end, Function<Double, Double> f) {
        super(eps, start, end, f);
    }

    private boolean pairwiseDifferent(Point x, Point w, Point v) {
        return x.compareToX(w) != 0 && x.compareToX(v) != 0 && w.compareToX(v) != 0;
    }


    Point findMinDot(Point left, Point right) {
        Point x1 = new Point(left.getX() + (1 - INV_GOLDEN_RATIO) * (right.getX() - left.getX()));
        Point x2 = new Point(left.getX() + INV_GOLDEN_RATIO * (right.getX() - left.getX()));
        return x1.compareToY(x2) < 0 ? x1 : x2;
    }

    @Override
    public double findMin() {
        Point left = new Point(start);
        Point right = new Point(end);
        Point x = new Point((end + start) / 2);
        Point w = new Point(x);
        Point v = new Point(x);
        double pred_len = (end - start);
        intermediateSegments.clear();
        intermediateSegments.add(new Segment(left.getX(), right.getX()));
        while (true) {
            boolean isSuccessiveParabolic = false;
            Point u = new Point(x);
            if (pairwiseDifferent(x, w, v)) { // use SuccessiveParabolicMethod
                List<Point> points = new ArrayList<>();
                points.add(x);
                points.add(w);
                points.add(v);
                points.sort(null);
                u = new Point(SuccessiveParabolicMethod.findMinDot(points.get(0), points.get(1), points.get(2)));
                if (u.compareToX(left) > 0 && u.compareToX(right) < 0) {
                    if (pred_len * 2 > lenOX(u, x)) {
                        isSuccessiveParabolic = true;
                    }
                }
            }
            if (!isSuccessiveParabolic) { // use GoldenRatioMethod
                if (lenOX(left, x) > lenOX(x, right))
                    u = findMinDot(left, x);
                else
                    u = findMinDot(x, right);
            }
            if (x.compareToX(u) == 0)
                return u.getX();
            v.set(w);
            w.set(x);
            pred_len = lenOX(left, right);
            SuccessiveParabolicMethod.recountPoint(left, x, right, u);
            x.set(u);
            intermediateSegments.add(new Segment(left.getX(), right.getX()));
        }
    }
}
