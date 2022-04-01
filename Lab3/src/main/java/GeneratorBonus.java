import java.util.Random;

public class GeneratorBonus implements Generator{
    private final int n;

    public final int sign;

    public  GeneratorBonus(int n, int sign) {
        this.n = n;
        this.sign = sign;
    }


    @Override
    public double[][] generate(int k) {
        double[][] ans = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                ans[i][j] = sign * rand.nextInt(5);
                ans[j][i] = ans[i][j];
            }
        }
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < n; j++) {
                sum += ans[i][j];
            }
            ans[i][i] = -sum + 1;
        }
        return ans;
    }
}
