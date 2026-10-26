package Question7;


public class CacheLocalityBenchmark {
    private static final int N = 2000;
    private static final int ITERATIONS = 10;

    private static final double[][] matrix = new double[N][N];

    // Prevent the computed results from being discarded.
    private static volatile double resultSink;

    public static void main(String[] args) {
        initializeMatrix();

        // Warm up the JVM before measuring performance.
        for (int i = 0; i < 3; i++) {
            resultSink = traverseRowMajor();
            resultSink = traverseColumnMajor();
        }

        long rowTotal = 0;
        long columnTotal = 0;

        for (int i = 1; i <= ITERATIONS; i++) {
            long start = System.nanoTime();
            resultSink = traverseRowMajor();
            long rowTime = System.nanoTime() - start;

            start = System.nanoTime();
            resultSink = traverseColumnMajor();
            long columnTime = System.nanoTime() - start;

            rowTotal += rowTime;
            columnTotal += columnTime;

            System.out.printf(
                    "Iteration %d | Row-major: %.3f ms"
                    + " | Column-major: %.3f ms%n",
                    i,
                    rowTime / 1_000_000.0,
                    columnTime / 1_000_000.0);
        }

        double avgRowMs =
                rowTotal / (double) ITERATIONS / 1_000_000.0;

        double avgColumnMs =
                columnTotal / (double) ITERATIONS / 1_000_000.0;

        double speedup = avgRowMs > 0
                ? avgColumnMs / avgRowMs : 0;

        System.out.println("\n--- Cache Locality Report ---");
        System.out.println("Matrix dimensions: " + N + " x " + N);
        System.out.println("Total elements: " + ((long) N * N));
        System.out.println("Iterations: " + ITERATIONS);

        System.out.printf("Average row-major time: %.3f ms%n",
                          avgRowMs);
        System.out.printf("Average column-major time: %.3f ms%n",
                          avgColumnMs);
        System.out.printf("Row-major speedup: %.2fx%n", speedup);

        System.out.println("Result checksum: " + resultSink);
    }

    private static void initializeMatrix() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = i * 0.5 + j * 0.25;
            }
        }
    }

    public static double traverseRowMajor() {
        double sum = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum += matrix[i][j];
            }
        }

        return sum;
    }

    public static double traverseColumnMajor() {
        double sum = 0;

        for (int j = 0; j < N; j++) {
            for (int i = 0; i < N; i++) {
                sum += matrix[i][j];
            }
        }

        return sum;
    }
}
