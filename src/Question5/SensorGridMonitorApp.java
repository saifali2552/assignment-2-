package Question5;


import java.util.concurrent.*;

public class SensorGridMonitorApp {
    public static void main(String[] args)
            throws InterruptedException, ExecutionException {

        int size = 50_000;
        double[] sensorData = new double[size];

        for (int i = 0; i < size; i++) {
            sensorData[i] = 20 + Math.random() * 80;
        }

        // Add known critical readings for verification.
        sensorData[123] = -5.5;
        sensorData[45678] = 150.0;

        ExecutorService monitor =
                Executors.newFixedThreadPool(2);

        long startTime = System.nanoTime();

        try {
            Future<Double> minimumFuture = monitor.submit(
                    () -> new ParallelMinSearch(
                            sensorData, 0, sensorData.length)
                            .processGrid());

            Future<Double> maximumFuture = monitor.submit(
                    () -> new ParallelMaxSearch(
                            sensorData, 0, sensorData.length)
                            .processGrid());

            double minimum = minimumFuture.get();
            double maximum = maximumFuture.get();

            long elapsed = System.nanoTime() - startTime;

            System.out.println("--- Sensor Grid Report ---");
            System.out.println("Total readings: " + size);
            System.out.println("Worker threads per search: 4");
            System.out.println("Minimum temperature: " + minimum);
            System.out.println("Maximum vibration reading: " + maximum);

            System.out.printf("Execution time: %.3f ms%n",
                    elapsed / 1_000_000.0);
        } finally {
            monitor.shutdown();
        }
    }
}

