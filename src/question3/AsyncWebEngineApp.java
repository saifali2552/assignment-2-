package Question3;


public class AsyncWebEngineApp {
    public static void main(String[] args)
            throws InterruptedException {

        System.out.println("Default thread: "
                + Thread.currentThread().getName());

        TelemetryThread telemetry = new TelemetryThread();
        telemetry.setName("Telemetry-Worker");

        LogProcessorTask logTask = new LogProcessorTask();
        Thread logThread = new Thread(logTask, "Log-Worker");
 System.out.println("Starting worker threads...");

        telemetry.start();
        logThread.start();

        System.out.println("Telemetry state: "
                + telemetry.getState());
        System.out.println("Log worker state: "
                + logThread.getState());

        telemetry.join();
        logThread.join();

        System.out.println("Telemetry final state: "
                + telemetry.getState());
        System.out.println("Log worker final state: "
                + logThread.getState());
        System.out.println("All background tasks completed.");
    }
       
}

