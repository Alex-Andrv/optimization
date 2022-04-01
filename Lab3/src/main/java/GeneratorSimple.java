public class GeneratorSimple implements Generator{

    private final int valueLimit;

    GeneratorSimple(int valueLimit) {
        this.valueLimit = valueLimit;
    }


    @Override
    public double[][] generate(int size) {
        double[][] matrix = new double[size][size];
        for (int i = 0; i < size; ++i) {
            matrix[i][i] = 1 + rand.nextInt(valueLimit);
            for (int j = 0; j < i; ++j) {
                if (rand.nextBoolean()) {
                    matrix[i][j] = matrix[j][i] = 0;
                } else {
                    matrix[i][j] = 1 + rand.nextInt(valueLimit);
                    matrix[j][i] = 1 + rand.nextInt(valueLimit);
                }
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        return matrix;
    }
}
