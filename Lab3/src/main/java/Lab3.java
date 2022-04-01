import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class Lab3 {
    public static final int SIZE_LIMIT = 10;
    public static final int VALUES_LIMIT = 40;

    public static void generateTests(Path path, int cnt, Solver solver) throws IOException {
        System.out.println("Generating " + cnt + " tests in " + path.toString());
        for (int i = 0; i < cnt; ++i) {
            Path testDir = Files.createDirectories(path.resolve("test" + i));
            Random random = new Random();
            int size = 1 + random.nextInt(SIZE_LIMIT);
            Matrix matrix;
            if (solver instanceof ConjugateGradientMethod) {
                matrix = new SparseMatrix(new GeneratorSimple(VALUES_LIMIT).generate(size));
            } else {
                matrix = new ProfileMatrix(new GeneratorSimple(VALUES_LIMIT).generate(size));
            }
            TestData testData = new TestData(matrix, new Vector(
                    random.ints(size, 0, VALUES_LIMIT)
                            .asDoubleStream().boxed().collect(Collectors.toList())));
            try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(testDir.resolve("in.data")))) {
                oos.writeObject(testData);
            }
            runTest(testDir, solver);
        }
        System.out.println("Done!");
    }

    public static void runTest(Path pathToTest, Solver solver) throws IOException {
        System.out.println("Running test in directory: " + pathToTest.toString());
        TestData testData = null;
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(pathToTest.resolve("in.data")))) {
            testData = (TestData) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(testData);
        try (BufferedWriter bw = Files.newBufferedWriter(pathToTest.resolve("out.txt"))) {
            Matrix matrix = testData.getMatrix();
            bw.write(matrix.toString());
            bw.newLine();
            bw.write(StringUtils.prettyVector("Right side vector", testData.getRhsVector()));
            bw.newLine();
            Vector x = solver.solve(matrix, testData.getRhsVector());
            bw.write(StringUtils.prettyVector("Solution", x));
        }
    }

    public static void startForSecondParagraphResearch(Path path, int k, int n,
                                                       Generator generator, Solver solver)
            throws IOException {
        Vector x = Vector.generateRandomVector(n, VALUES_LIMIT);
        Files.createDirectories(path);
        try (BufferedWriter result = Files.newBufferedWriter(path.resolve("result.txt"))) {
            for (int ki = 0; ki < k; ki++) {
                research(result, path, ki, generator, x, n, solver);
            }
        }
    }

    public static void research(BufferedWriter result, Path path, int ki,
                                Generator generator, Vector x, int n, Solver solver)
            throws IOException {
        Matrix A;
        if (solver instanceof ConjugateGradientMethod) {
            A = new SparseMatrix(generator.generate(ki));
        } else {
            A = new ProfileMatrix(generator.generate(ki));
        }
        final Vector B = A.multiplicationForVector(x);
        Path testDir = Files.createDirectories(path.resolve("test" + ki));
        try (BufferedWriter bw = Files.newBufferedWriter(testDir.resolve("out.txt"))) {
            bw.write(A.toString());
            bw.newLine();
            bw.write(StringUtils.prettyVector("Right side vector", B));
            bw.newLine();
            bw.write(StringUtils.prettyVector("Exact solution", x));
            TestData testData = new TestData(A, B);
            try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(testDir.resolve("in.data")))) {
                oos.writeObject(testData);
            }
            Vector xNew = solver.solve(A, B);
            bw.write(StringUtils.prettyVector("Solution", xNew));
            double absoluteErrorForX = x.sub(xNew).norm();
            double relativeErrorForX = absoluteErrorForX / x.norm();
            double absoluteErrorForF = B.sub(A.multiplicationForVector(xNew)).norm();
            double relativeErrorForF = absoluteErrorForF / B.norm();
            double cond = relativeErrorForX / relativeErrorForF;
            if (solver instanceof ConjugateGradientMethod) {
                ki = ((ConjugateGradientMethod) solver).getCnt();
            }
            result.write(n + " & " + ki + " & " + absoluteErrorForX
                    + " & " + relativeErrorForX + " & " +
                    cond
                    + "\\\\");
            result.newLine();

        }
    }

    public static void startHilbertResearch(Path path, int k, Generator generator, Solver solver)
            throws IOException {
        Files.createDirectories(path);
        try (BufferedWriter result = Files.newBufferedWriter(path.resolve("result.txt"))) {
            for (int ki = 0; ki < k; ki++) {
                int len = 2 + ki;
                Vector x = Vector.generateRandomVector(len, VALUES_LIMIT);
                research(result, path, len, generator, x, len, solver);
            }
        }

    }

    public static void printUsage() {
        System.err.println("Generate and run tests usage: " +
                "Lab3 generate <tests directory path> <number of tests> [bonus(optional)]");
        System.err.println("Research for second paragraph: " +
                "Lab3 research  forSecondParagraph <n parameter> <max k parameter> <tests directory path> [bonus]");
        System.err.println("Research for Hilbert matrix: " +
                "Lab3 research Hilbert <max k parameter> <tests directory path> [bonus(optional)]");
        System.err.println("Run custom test usage: " +
                "Lab3 run <path to test directory> [bonus(optional)]");
    }

    public static void main(String[] args) throws IOException {
        if (args == null || args.length < 2 || args.length > 6) {
            printUsage();
            return;
        }
        for (String arg : args) {
            if (arg == null) {
                printUsage();
                return;
            }
        }
        Solver solver;
        if (Arrays.asList(args).contains("bonus")) {
            solver = new ConjugateGradientMethod(1e-12);
        } else {
            solver = new GaussMethod();
        }
        if ("generate".equals(args[0])) {
            generateTests(Path.of(args[1]), Integer.parseInt(args[2]), solver);
        } else if ("run".equals(args[0])) {
            runTest(Path.of(args[1]), solver);
        } else if ("research".equals(args[0])) {
            switch (args[1]) {
                case "forSecondParagraph" -> {
                    int n = Integer.parseInt(args[2]);
                    Generator generator = new GeneratorMatrixForSecondParagraph(n);
                    if (args.length == 6) {
                        generator = new GeneratorBonus(n, 1);
                    }

                    startForSecondParagraphResearch(Path.of(args[4]), Integer.parseInt(args[3]),
                            n, generator, solver);
                }
                case "Hilbert" -> {
                    if (args.length < 4) {
                        printUsage();
                    }
                    startHilbertResearch(Path.of(args[3]), Integer.parseInt(args[2]),
                            new GeneratorHilbertMatrix(), solver);
                }
            }
        } else {
            printUsage();
        }
    }
}