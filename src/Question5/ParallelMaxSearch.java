package Question5;

import java.util.concurrent.*;

public class ParallelMaxSearch extends SensorSearchEngine {
    public ParallelMaxSearch(
            double[] data, int startIdx, int endIdx) {
        super(data, startIdx, endIdx);
    }

    @Override
    public double processGrid() {
        int workers = 4;
        ExecutorService pool = Executors.newFixedThreadPool(workers);

        try {
            @SuppressWarnings("unchecked")
            Future<Double>[] results = new Future[workers];

            int length = endIdx - startIdx;
            int chunkSize = (length + workers - 1) / workers;

            for (int w = 0; w < workers; w++) {
                final int from = startIdx + w * chunkSize;
                final int to = Math.min(
                        from + chunkSize, endIdx);

                results[w] = pool.submit(() -> {
                    double maximum = Double.NEGATIVE_INFINITY;

                    for (int i = from; i < to; i++) {
                        maximum = Math.max(
                                maximum, sensorData[i]);
                    }

                    return maximum;
                });
            }

            double maximum = Double.NEGATIVE_INFINITY;

            for (Future<Double> result : results) {
                maximum = Math.max(maximum, result.get());
            }

            return maximum;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Maximum search interrupted", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Maximum search failed", e);
        } finally {
            pool.shutdown();
        }
    }
}

