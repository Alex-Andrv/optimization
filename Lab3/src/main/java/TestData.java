import java.io.Serializable;
import java.util.List;

public class TestData implements Serializable {
    private final Matrix matrix;
    private final Vector rhsVector;

    public TestData(Matrix matrix, Vector rhsVector) {
        this.matrix = matrix;
        this.rhsVector = rhsVector;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public Vector getRhsVector() {
        return rhsVector;
    }
}
