package gradientDescents;

public class DiagonalMatrix extends Matrix {

    public double[] diag;

    public DiagonalMatrix(double[] values) {
        super();
        this.diag = values;
        this.rowCount = values.length;
        this.columnCount = values.length;
    }

    @Override
    public double get(int i, int j) {
        return i == j ? diag[i] : 0;
    }

    public void set(int i, int j, double val) {
        if (i == j) {
            diag[i] = val;
        }
    }

    public Matrix multiply(Matrix rhs) {
        Matrix res = new Matrix(rowCount, rhs.columnCount);
        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < rhs.columnCount; ++j) {
                res.set(i, j, res.get(i, j) + get(i, i) * rhs.get(i, j));
            }
        }
        return res;
    }

    public Matrix multiply(Vector rhs) {
        Matrix res = new Matrix(rowCount, 1);
        for (int i = 0; i < rowCount; ++i) {

        }
        return res;
    }

}
