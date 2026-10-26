package Question3;


public class TelemetryThread extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            double cpuUsage = 20 + i * 3.5;
            double memoryUsage = 40 + i * 2.0;

            System.out.printf(
                    "%s | Update %d | CPU: %.1f%% | Memory: %.1f%%%n",
                    Thread.currentThread().getName(),
                    i, cpuUsage, memoryUsage);

            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Telemetry thread interrupted.");
                return;
            }
        }
    }
}

