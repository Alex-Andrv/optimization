public class Vector extends Matrix {
    public Vector(int n) {
        super(n, 1);
    }

    public Vector(double... coords) {
        this(coords.length);
        for (int i = 0; i < getRowCount(); ++i) {
            set(i, coords[i]);
        }
    }

    public Vector(Matrix matrix, int toExtractColumn) {
        this(matrix.getRowCount());
        for (int i = 0; i < getRowCount(); ++i) {
            set(i, matrix.get(i, toExtractColumn));
        }
    }

    public Vector(Matrix vector) {
        this(vector, 0);
    }

    public double norm() {
        double norm = 0;
        for (int i = 0; i < getRowCount(); ++i) {
            norm += get(i) * get(i);
        }
        return Math.sqrt(norm);
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
        if (rowCount != rhs.rowCount) {
            throw new IllegalArgumentException();
        }
        return new Vector(super.add(rhs));
    }

    public Vector sub(Vector rhs) {
        if (rowCount != rhs.rowCount) {
            throw new IllegalArgumentException();
        }
        return new Vector(super.sub(rhs));
    }

    public Vector multiplyByConst(double c) {
        return new Vector(super.multiplyByConst(c));
    }

    public Vector delByConst(double c) {
        return new Vector(super.delByConst(c));
    }

    public double dotProduct(Vector rhs) {
        return transposed().multiply(rhs).get(0, 0);
    }
}