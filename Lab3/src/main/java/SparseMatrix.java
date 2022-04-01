import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SparseMatrix extends Matrix{
    private final List<Integer> ja;

    private SparseMatrix(List<Double> di, List<Double> al, List<Double> au, List<Integer> ia, List<Integer> ja, int n) {
        super(di, al, au, ia, n);
        this.ja = ja;
    }

    public SparseMatrix(List<Double> di, List<Double> al, List<Double> au, List<Integer> ia, List<Integer> ja) {
        this(di, al, au, ia, ja, di.size());
    }

    public SparseMatrix(double[][] values) {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), values.length);
        for (int i = 0; i < n; ++i) {
            di.add(values[i][i]);
        }
        ia.add(0);
        ia.add(0);
        for (int i = 1; i < n; ++i) {
            int countElem = 0;
            for (int j = 0; j < i; ++j) {
                if (values[i][j] == 0) {
                    continue;
                }
                countElem++;
                al.add(values[i][j]);
                au.add(values[j][i]);
                ja.add(j);
            }
            ia.add(ia.get(ia.size() - 1) + countElem);
        }
    }

    public int getSize() {
        return n;
    }

    @Override
    protected double get(List<Double> values, int i, int j) {
        for (int iter = ia.get(i); iter < ia.get(i + 1) && ja.get(iter) <= j; iter++) {
            if (ja.get(iter) == j) {
                return values.get(iter);
            }
        }
        return 0;
    }

    @Override
    protected void set(List<Double> values, int i, int j, double value) {
        throw new UnsupportedOperationException("This method is not used in our research. " +
                "But may be added in the future");
    }



    @Override
    public String toString() {
        Map<String, List<? extends Number>> namedList = new HashMap<>();
        namedList.put("ja", ja);
        namedList.put("di", di);
        namedList.put("al", al);
        namedList.put("au", au);
        namedList.put("ia", ia);

        return toString(namedList);
    }
}
