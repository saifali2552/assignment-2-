package Question4;


import java.util.concurrent.ForkJoinPool;

public class ForkJoinPortfolioApp {
    public static void main(String[] args) {
        int size = 10_000;
        double[] assets = new double[size];

        for (int i = 0; i < size; i++) {
            assets[i] = 100 + (i % 500);
        }

        ForkJoinPool pool = new ForkJoinPool();

        // Sequential baseline before modifying the array.
        long serialStart = System.nanoTime();
        double serialSum = 0;

        for (double value : assets) {
            serialSum += value;
        }

        long serialTime = System.nanoTime() - serialStart;

        // Parallel risk calculation.
        long parallelStart = System.nanoTime();

        double initialRisk = pool.invoke(
                new ParallelRiskCalculator(
                        assets, 0, assets.length));

        long parallelRiskTime =
                System.nanoTime() - parallelStart;

        // Apply stress testing after measuring initial risk.
        long stressStart = System.nanoTime();

        pool.invoke(new PortfolioThresholdTask(
                assets, 0, assets.length, 400));

        long stressTime = System.nanoTime() - stressStart;

        // Recalculate risk after the adjustment.
        double adjustedRisk = pool.invoke(
                new ParallelRiskCalculator(
                        assets, 0, assets.length));

        System.out.println("--- Portfolio Assessment ---");
        System.out.printf("Serial total: %.2f%n", serialSum);
        System.out.printf("Parallel initial risk: %.2f%n",
                          initialRisk);
        System.out.printf("Adjusted risk total: %.2f%n",
                          adjustedRisk);

        System.out.printf("Serial time: %.3f ms%n",
                          serialTime / 1_000_000.0);
        System.out.printf("Parallel risk time: %.3f ms%n",
                          parallelRiskTime / 1_000_000.0);
        System.out.printf("Stress-test time: %.3f ms%n",
                          stressTime / 1_000_000.0);

        if (parallelRiskTime > 0) {
            System.out.printf(
                    "Measured speedup: %.2fx%n",
                    (double) serialTime / parallelRiskTime);
        }

        pool.shutdown();
    }
}

