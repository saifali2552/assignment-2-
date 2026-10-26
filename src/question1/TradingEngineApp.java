
package Question1;


public class TradingEngineApp {
    public static void main(String[] args) {
        TradeEvent event1 = new FIXOrderEvent(
                "FIX-Gateway-01", System.currentTimeMillis(),
                1000.00, "ORD-101");

        TradeEvent event2 = new ReutersQuoteEvent(
                "Reuters-Gateway-01", System.currentTimeMillis(),
                150.00, 149.80, 150.20);

        TradeEvent event3 = new TradeEvent(
                "Generic-Gateway", System.currentTimeMillis(), 250.00);

        event1.dispatch();
        event2.dispatch();
        event3.dispatch();
    }
}

