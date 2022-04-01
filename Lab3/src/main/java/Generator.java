import java.util.Random;

public interface Generator {

    Random rand = new Random();

    double[][] generate(int  k);

    default double nextDouble() {
        return rand.nextDouble() * (100) - 50;
    }

    default boolean nextBoolean() {
        return rand.nextBoolean();
    }

}
