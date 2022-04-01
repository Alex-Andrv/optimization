public class GeneratorHilbertMatrix implements Generator {

    @Override
    public double[][] generate(int n) {
        double[][] ans = new double[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++) {
                ans[i][j] = 1d / (i + j + 1); // 1 / ( (i + 1) + (j + 1) - 1)
            }
        }
        return ans;
    }
}
