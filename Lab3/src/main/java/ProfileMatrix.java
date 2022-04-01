import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ProfileMatrix extends Matrix {

    private ProfileMatrix(List<Double> di, List<Double> al, List<Double> au, List<Integer> ia, int n) {
        super(di, al, au, ia, n);
    }

    /**
     * Constructs square profile matrix n x n by arrays.
     *
     * @param di main diagonal. |di| = n
     * @param al low triangle by rows.
     * @param au upper triangle by columns.
     * @param ia profile indices. Last element represents index of next free value in al and au. |ia| = n + 1
     */
    public ProfileMatrix(List<Double> di, List<Double> al, List<Double> au, List<Integer> ia) {
        this(di, al, au, ia, di.size());
    }

    public ProfileMatrix(double[][] values) {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), values.length);
        for (int i = 0; i < n; ++i) {
            di.add(values[i][i]);
        }
        ia.add(0);
        ia.add(0);
        for (int i = 1; i < n; ++i) {
            int profileSize = 0;
            for (int j = 0; j < i; ++j) {
                if (profileSize == 0 && values[i][j] == 0) {
                    continue;
                }
                profileSize++;
                al.add(values[i][j]);
                au.add(values[j][i]);
            }
            ia.add(ia.get(ia.size() - 1) + profileSize);
        }
    }

    public int getSize() {
        return n;
    }

    protected double get(List<Double> values, int i, int j) {
        int profileSize = ia.get(i + 1) - ia.get(i);
        int profileStart = i - profileSize;
        return j >= profileStart ? values.get(ia.get(i) + j - profileStart) : 0;
    }

    protected void set(List<Double> values, int i, int j, double value) {
        int profileSize = ia.get(i + 1) - ia.get(i);
        int profileStart = i - profileSize;
        if (j >= profileStart) {
            values.set(ia.get(i) + j - profileStart, value);
        }
    }


    public double[][] toArray() {
        double[][] res = new double[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                res[i][j] = get(i, j);
            }
        }
        return res;
    }

    public ProfileMatrix luDecomposed() {
        for (int i = 1; i < n; ++i) {
            for (int j = 0; j <= i; ++j) {
                double lVal = get(i, j);
                double uVal = get(j, i);
                for (int k = 0; k < j; ++k) {
                    lVal -= get(i, k) * get(k, j);
                    uVal -= get(j, k) * get(k, i);
                }
                set(j, i, uVal / get(j, j));
                set(i, j, lVal);
            }
        }
        return this;
    }


    @Override
    public String toString() {
        Map<String, List<? extends Number>> namedList = new HashMap<>();
        namedList.put("di", di);
        namedList.put("al", al);
        namedList.put("au", au);
        namedList.put("ia", ia);

        return toString(namedList);
    }
}
