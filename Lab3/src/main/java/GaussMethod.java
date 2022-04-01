import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GaussMethod implements Solver {


    private  Vector forwardSubstitution(ProfileMatrix a, Vector b) {
        Vector y = new Vector(a.getSize());
        y.set(0, b.get(0) / a.get(0, 0));
        for (int i = 1; i < a.getSize(); ++i) {
            y.set(i, b.get(i));
            for (int j = 0; j < i; ++j) {
                y.set(i, y.get(i) - a.get(i, j) * y.get(j));
            }
            y.set(i, y.get(i) / a.get(i, i));
        }
        return y;
    }

    private  Vector backwardSubstitution(ProfileMatrix a, Vector y) {
        Vector x = new Vector(a.getSize());
        x.set(x.size() - 1, y.get(y.size() - 1));
        for (int i = x.size() - 2; i >= 0; --i) {
            x.set(i, y.get(i));
            for (int j = i + 1; j < a.getSize(); ++j) {
                x.set(i, x.get(i) - a.get(i, j) * x.get(j));
            }
        }
        return x;
    }

    public Vector solve(Matrix a, Vector b) {
        if (a instanceof ProfileMatrix) {
            ProfileMatrix profileMatrix = (ProfileMatrix) a;
            return backwardSubstitution(profileMatrix,
                    forwardSubstitution(profileMatrix.luDecomposed(), b));
        } else {
            throw new IllegalArgumentException();
        }
    }
}
