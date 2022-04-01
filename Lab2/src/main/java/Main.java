import gradientDescents.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

public class Main {
    public static void test_high_mu() {
        Matrix a = new Matrix(new double[][]{
                {6, -5},
                {-5, 6}});
        Vector b = new Vector(-7, 2);
        double c = -30;
        Vector start = new Vector(10, 10);
        QuadraticFunction qf = new QuadraticFunction(a, b, c);
        var res = new GradientDescent(qf, start, 1e-6, 0.1).findMin();
        var res1 = new SteepestGradientDescent(qf, start, 1e-6).findMin();
        var res2 = new ConjugateGradientMethod(qf, start, 1e-6).findMin();
    }

    public static void test_2_quad() {
        Matrix a = new DiagonalMatrix(new double[]
                {2 * 64, 126});
        Vector b = new Vector(-10, 30);
        double c = 13;
        QuadraticFunction form = new QuadraticFunction(a, b, c);
        SteepestGradientDescent gradientDescent = new SteepestGradientDescent(form, new Vector(10, 10), 1e-6);
        ConjugateGradientMethod conjugateGradient = new ConjugateGradientMethod(form, new Vector(10, 10), 1e-6);
        GradientDescent simpleGradientDescent = new GradientDescent(form, new Vector(10, 10), 1e-6, 0.1);
        Vector min = gradientDescent.findMin();
        System.out.println(min.get(0));
        System.out.println(min.get(1));
        Vector minConjugate = conjugateGradient.findMin();
        System.out.println(minConjugate.get(0));
        System.out.println(minConjugate.get(1));
        Vector minGradient = simpleGradientDescent.findMin();
        System.out.println(minGradient.get(0));
        System.out.println(minGradient.get(1));
    }

    public static void main(String[] args) {
        test_high_mu();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt", StandardCharsets.UTF_8))) {
            Random random = new Random();
            for (int i = 1; i < 5; i++) {
                int n = (int) Math.pow(10, i);
                for (int k = 100; k <= 2000; k += 400) {
                    double[] nums = new double[n];
                    for (int j = 0; j < n - 2; j++) {
                        nums[j] = (random.nextDouble() * (k - 1 - 2)) + 2;
                    }
                    nums[n - 2] = 1;
                    nums[n - 1] = k;
                    Arrays.sort(nums);
                    double[] coords = new double[n];
                    Arrays.fill(coords, 10);
                    Vector start = new Vector(coords);
                    QuadraticFunction form = new QuadraticFunction(new DiagonalMatrix(nums), new Vector(n), 0);
                    SteepestGradientDescent gradientDescent = new SteepestGradientDescent(form, start, 1e-4);
                    gradientDescent.findMin();
                    System.out.println("n=" + n + " k=" + k);
                    writer.write(String.valueOf(gradientDescent.counter));
                    writer.write(" & ");
                }
                writer.write("\\\\");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
