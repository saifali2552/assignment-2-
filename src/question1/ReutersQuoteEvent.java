package Question1;

public class ReutersQuoteEvent extends TradeEvent {
    private double bidPrice;
    private double askPrice;

    public ReutersQuoteEvent(String senderId, long timestamp,
                             double basePrice,
                             double bidPrice, double askPrice) {
        super(senderId, timestamp, basePrice);
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
    }
@Override
    public void dispatch() {
        double spread = askPrice - bidPrice;
        double spreadPercentage =
                bidPrice != 0 ? (spread / bidPrice) * 100 : 0;

        System.out.println("\n--- Reuters Market Quote ---");
        System.out.println("Sender ID: " + senderId);
        System.out.println("Timestamp: " + timestamp);
        System.out.printf("Bid Price: %.2f%n", bidPrice);
        System.out.printf("Ask Price: %.2f%n", askPrice);
        System.out.printf("Bid-Ask Spread: %.2f%n", spread);
        System.out.printf("Spread Percentage: %.3f%%%n",
                          spreadPercentage);
    }

}
