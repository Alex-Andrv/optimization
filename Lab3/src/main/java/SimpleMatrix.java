import java.io.Serializable;

public class SimpleMatrix implements Serializable {
    private double[][] values;
    protected int rowCount;
    protected int columnCount;

    public SimpleMatrix(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        values = new double[rowCount][columnCount];
    }

    public SimpleMatrix(double[][] values) {
        this.values = values;
        this.rowCount = values.length;
        this.columnCount = values[0].length;
    }

    public SimpleMatrix() {

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

    public SimpleMatrix add(SimpleMatrix rhs) {
        SimpleMatrix res = new SimpleMatrix(rowCount, columnCount);
        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < columnCount; ++j) {
                res.set(i, j, get(i, j) + rhs.get(i, j));
            }
        }
        return res;
    }

    public SimpleMatrix sub(SimpleMatrix rhs) {
        return add(rhs.multiplyByConst(-1));
    }

    public SimpleMatrix multiplyByConst(double c) {
        SimpleMatrix res = new SimpleMatrix(rowCount, columnCount);
        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < columnCount; ++j) {
                res.set(i, j, get(i, j) * c);
            }
        }
        return res;
    }

    public SimpleMatrix multiply(SimpleMatrix rhs) {
        SimpleMatrix res = new SimpleMatrix(rowCount, rhs.columnCount);
        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < rhs.columnCount; ++j) {
                for (int k = 0; k < columnCount; ++k) {
                    res.set(i, j, res.get(i, j) + get(i, k) * rhs.get(k, j));
                }
            }
        }
        return res;
    }

    public SimpleMatrix transposed() {
        SimpleMatrix res = new SimpleMatrix(columnCount, rowCount);
        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < columnCount; ++j) {
                res.set(j, i, get(i, j));
            }
        }
        return res;
    }
}
