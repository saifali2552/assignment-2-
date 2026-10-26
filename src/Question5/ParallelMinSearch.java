package Question5;
import java.util.concurrent.*;

public class ParallelMinSearch extends SensorSearchEngine {
    public ParallelMinSearch(
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
                    double minimum = Double.POSITIVE_INFINITY;

                    for (int i = from; i < to; i++) {
                        minimum = Math.min(
                                minimum, sensorData[i]);
                    }

                    return minimum;
                });
            }

            double minimum = Double.POSITIVE_INFINITY;

            for (Future<Double> result : results) {
                minimum = Math.min(minimum, result.get());
            }

            return minimum;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Minimum search interrupted", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Minimum search failed", e);
        } finally {
            pool.shutdown();
        }
    }
}
