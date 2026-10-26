package Question4;


import java.util.concurrent.RecursiveAction;

public class PortfolioThresholdTask extends RecursiveAction {
    private static final int THRESHOLD = 1000;

    private final double[] assets;
    private final int start;
    private final int end;
    private final double riskCap;

    public PortfolioThresholdTask(
            double[] assets, int start, int end,
            double riskCap) {
        this.assets = assets;
        this.start = start;
        this.end = end;
        this.riskCap = riskCap;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                if (assets[i] > riskCap) {
                    assets[i] *= 1.05;
                }
            }
            return;
        }

        int middle = start + (end - start) / 2;

        invokeAll(
            new PortfolioThresholdTask(
                assets, start, middle, riskCap),
            new PortfolioThresholdTask(
                assets, middle, end, riskCap)
        );
    }
}
