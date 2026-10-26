package Question6;


import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class InventoryCatalogManager {
    private static final int WORKERS = 10;
    private static final int OPERATIONS_PER_WORKER = 50_000;
    private static final int PRODUCT_COUNT = 1000;

    private Map<String, Integer> createInitialCatalog() {
        Map<String, Integer> catalog = new HashMap<>();

        for (int i = 0; i < PRODUCT_COUNT; i++) {
            catalog.put("P" + i, 100);
        }

        return catalog;
    }

    public void benchmarkSynchronizedMap()
            throws InterruptedException {
        Map<String, Integer> catalog =
                Collections.synchronizedMap(createInitialCatalog());

        runBenchmark("Collections.synchronizedMap", catalog);
    }

    public void benchmarkConcurrentMap()
            throws InterruptedException {
        ConcurrentHashMap<String, Integer> catalog =
                new ConcurrentHashMap<>(createInitialCatalog());

        runBenchmark("ConcurrentHashMap", catalog);
    }

    private void runBenchmark(
            String name, Map<String, Integer> catalog)
            throws InterruptedException {

        ExecutorService pool =
                Executors.newFixedThreadPool(WORKERS);

        CountDownLatch ready = new CountDownLatch(WORKERS);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(WORKERS);

        AtomicLong completed = new AtomicLong();

        for (int worker = 0; worker < WORKERS; worker++) {
            final int workerId = worker;

            pool.execute(() -> {
                ready.countDown();

                try {
                    start.await();

                    for (int i = 0;
                         i < OPERATIONS_PER_WORKER; i++) {

                        String id = "P" + (
                                (i + workerId) % PRODUCT_COUNT);

                        // Lookup
                        catalog.get(id);

                        // Atomic stock update.
                        if (catalog instanceof ConcurrentHashMap) {
                            ((ConcurrentHashMap<String, Integer>) catalog)
                                    .merge(id, 1, Integer::sum);
                        } else {
                            synchronized (catalog) {
                                catalog.put(id, catalog.get(id) + 1);
                            }
                        }

                        completed.addAndGet(2);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();
                }
            });
        }

        ready.await();

        long startTime = System.nanoTime();
        start.countDown();

        done.await();

        long elapsed = System.nanoTime() - startTime;
        double seconds = elapsed / 1_000_000_000.0;
        double throughput = completed.get() / seconds;

        System.out.println("\n--- " + name + " ---");
        System.out.println("Workers: " + WORKERS);
        System.out.println("Operations per worker: "
                + OPERATIONS_PER_WORKER);
        System.out.println("Completed operations: "
                + completed.get());
        System.out.printf("Execution time: %.3f ms%n",
                elapsed / 1_000_000.0);
        System.out.printf("Throughput: %.2f operations/second%n",
                throughput);

        pool.shutdown();
    }

    public static void main(String[] args)
            throws InterruptedException {

        InventoryCatalogManager manager =
                new InventoryCatalogManager();

        System.out.println("Inventory Concurrency Benchmark");

        manager.benchmarkSynchronizedMap();
        manager.benchmarkConcurrentMap();
    }
}

