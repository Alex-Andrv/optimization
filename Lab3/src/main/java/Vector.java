import java.util.List;
import java.util.Random;

public class Vector extends SimpleMatrix {
    public Vector(int n) {
        super(n, 1);
    }

    public Vector(List<Double> vector) {
        super(vector.size(), 1);
        for (int i = 0; i < vector.size(); i++) {
            set(i, vector.get(i));
        }
    }

    public Vector(double... coords) {
        this(coords.length);
        for (int i = 0; i < getRowCount(); ++i) {
            set(i, coords[i]);
        }
    }

    public Vector(SimpleMatrix simpleMatrix, int toExtractColumn) {
        this(simpleMatrix.getRowCount());
        for (int i = 0; i < getRowCount(); ++i) {
            set(i, simpleMatrix.get(i, toExtractColumn));
        }
    }

    public Vector(SimpleMatrix vector) {
        this(vector, 0);
    }

    public int size() {
        return getRowCount();
    }

    public double norm() {
        double norm = 0;
        for (int i = 0; i < getRowCount(); ++i) {
            norm += get(i) * get(i);
        }
        return Math.sqrt(norm);
    }

    public static Vector generateRandomVector(int n, int valueLimit) {
        Random rand = new Random();
        Vector ans = new Vector(n);
        for (int i = 0; i < n; i++) {
            ans.set(i, rand.nextInt(valueLimit));
        }
        return ans;
    }

    public Vector normalized() {
        return multiplyByConst(1 / norm());
    }

    public double get(int i) {
        return get(i, 0);
    }

    public void set(int i, double x) {
        set(i, 0, x);
    }

    public Vector add(Vector rhs) {
        return new Vector(super.add(rhs));
    }

    public Vector sub(Vector rhs) {
        return new Vector(super.sub(rhs));
    }

    public Vector multiplyByConst(double c) {
        return new Vector(super.multiplyByConst(c));
    }

    public double dotProduct(Vector rhs) {
        return transposed().multiply(rhs).get(0, 0);
    }
}
