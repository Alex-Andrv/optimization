import java.util.Random;

public class GeneratorMatrixForSecondParagraph implements Generator{

    private final int n;

    public  GeneratorMatrixForSecondParagraph(int n) {
        this.n = n;
    }


    @Override
    public double[][] generate(int k) {
        Random rand = new Random();
        double[][] ans = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
               ans[i][j] = nextDouble();
               ans[j][i] = ans[i][j];
            }
        }
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < n; j++) {
                sum += ans[i][j];
            }
            ans[i][i] = -sum + Math.pow(10, -k);
        }
        return ans;
    }
}
