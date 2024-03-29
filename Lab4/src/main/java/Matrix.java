public class Matrix {
    private double[][] values;
    protected int rowCount;
    protected int columnCount;

    public Matrix(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        values = new double[rowCount][columnCount];
    }


    public static Matrix getIdentityMatrix(int n) {
        double[][] value = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                value[i][j] = 0;
                if (i == j)
                    value[i][j] = 1;
            }
        }
        return new Matrix(value);
    }


    public Matrix(double[][] values) {
        this.values = values;
        this.rowCount = values.length;
        this.columnCount = values[0].length;
    }

    public Matrix() {

    }

    public double get(int i, int j) {
        return values[i][j];
    }

    public void set(int i, int j, double val) {
        values[i][j] = val;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public double[][] getValues() {
        return values;
    }

    public Matrix add(Matrix rhs) {
        if (columnCount != rhs.getColumnCount() || rowCount != rhs.getRowCount()) {
            throw  new IllegalArgumentException();
        }
        Matrix res = new Matrix(rowCount, columnCount);
        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < columnCount; ++j) {
                res.set(i, j, get(i, j) + rhs.get(i, j));
            }
        }
        return res;
    }

    public Matrix sub(Matrix rhs) {
        return add(rhs.multiplyByConst(-1));
    }

    public Matrix multiplyByConst(double c) {
        Matrix res = new Matrix(rowCount, columnCount);
        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < columnCount; ++j) {
                res.set(i, j, get(i, j) * c);
            }
        }
        return res;
    }

    public Matrix delByConst(double c) {
        Matrix res = new Matrix(rowCount, columnCount);
        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < columnCount; ++j) {
                res.set(i, j, get(i, j) / c);
            }
        }
        return res;
    }

    public Matrix multiply(Matrix rhs) {
        if (columnCount != rhs.getRowCount()) {
            throw new IllegalArgumentException();
        }
        Matrix res = new Matrix(rowCount, rhs.columnCount);
        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < rhs.columnCount; ++j) {
                for (int k = 0; k < columnCount; ++k) {
                    res.set(i, j, res.get(i, j) + get(i, k) * rhs.get(k, j));
                }
            }
        }
        return res;
    }

    public Matrix transposed() {
        Matrix res = new Matrix(columnCount, rowCount);
        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < columnCount; ++j) {
                res.set(j, i, get(i, j));
            }
        }
        return res;
    }
}