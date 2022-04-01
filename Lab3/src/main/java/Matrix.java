import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Matrix implements Serializable {

    protected final List<Double> di;
    protected final List<Double> al;
    protected final List<Double> au;
    protected final List<Integer> ia;
    protected final int n;

    protected Matrix(List<Double> di, List<Double> al, List<Double> au, List<Integer> ia, int n) {
        this.di = di;
        this.al = al;
        this.au = au;
        this.ia = ia;
        this.n = n;
    }


    public abstract int getSize();

    protected abstract double get(List<Double> values, int i, int j);

    public double get(int i, int j) {
        if (i == j) {
            return di.get(i);
        }
        return i < j ? get(au, j, i) : get(al, i, j);
    }

    protected abstract void set(List<Double> values, int i, int j, double value);

    public void set(int i, int j, double value) {
            if (i == j) {
                di.set(i, value);
                return;
            }
            if (i < j) {
                set(au, j, i, value);
            } else {
                set(al, i, j, value);
            }
    }

    public Vector multiplicationForVector(Vector vector) {
        if (n != vector.size()) {
            throw new IllegalArgumentException();
        }
        Vector ans = new Vector(n);
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < n; j++) {
                sum += get(i, j) * vector.get(j);
            }
            ans.set(i, sum);
        }
        return ans;
    }

    protected String toString(Map<String, List<? extends Number>> namedList) {
        StringBuilder sb = new StringBuilder();
        sb.append("Size = ").append(getSize()).append(System.lineSeparator())
                .append(namedList.entrySet().stream().map(s -> StringUtils.prettyList(s.getKey(), s.getValue()))
                        .collect(Collectors.joining ("")));
        sb.append(System.lineSeparator());
        sb.append("Dense representation").append(System.lineSeparator());
        for (int i = 0; i < getSize(); ++i) {
            for (int j = 0; j < getSize(); ++j) {
                sb.append(get(i, j)).append(" ");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public abstract String toString();
}
