
package Question1;

public class TradingEngineApp {
    public static void main(String[] args) {
        TradeEvent event = new TradeEvent(
            "TRADER-101",
            System.currentTimeMillis(),
            250.75
        );

        event.dispatch();
    }
}
