public class ModifiedGaussMethod {

    public static double[] solve(final double[][] a, final double[] b) {
        final int n = b.length;
        final double[] x = new double[n];
        for (int k = 0; k < n - 1; k++) {
            double max = a[k][k];
            int maxIndex = k;

            for (int i = k + 1; i < n; i++) {
                if (a[i][k] > max) {
                    max = a[i][k];
                    maxIndex = i;
                }
            }

            swap(b, maxIndex, k);
            swap(a, maxIndex, k);

            for (int i = k + 1; i < n; i++) {
                final double t = a[i][k] / a[k][k];
                b[i] = b[i] - t * b[k];
                for (int j = k + 1; j < n; j++) {
                    a[i][j] = a[i][j] - t * a[k][j];
                }
            }
        }

        x[n - 1] = b[n - 1] / a[n - 1][n - 1];
        for (int k = n - 2; k >= 0; k--) {
            x[k] = b[k];
            for (int j = k + 1; j < n; j++) {
                x[k] = x[k] - a[k][j] * x[j];
            }
            x[k] = x[k] / a[k][k];
        }

        return x;
    }

    private static void swap(double[][] a, int i, int j) {
        double[] tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static void swap(double[] a, int i, int j) {
        double tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
