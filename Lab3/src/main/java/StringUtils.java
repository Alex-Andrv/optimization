import java.util.List;
import java.util.StringJoiner;

public class StringUtils {
    public static String prettyList(String prefix, List<? extends Number> list) {
        StringJoiner sj = new StringJoiner(", ", prefix + " = [", "]" + System.lineSeparator());
        for (Number x : list) {
            sj.add(x.toString());
        }
        return sj.toString();
    }

    public static String prettyVector(String prefix, Vector vector) {
        StringJoiner sj = new StringJoiner(", ", prefix + " = [", "]" + System.lineSeparator());
        for (int i = 0; i < vector.size(); i++) {
            sj.add(Double.toString(vector.get(i)));
        }
        return sj.toString();
    }
}
