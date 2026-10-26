package Question1;


public class FIXOrderEvent extends TradeEvent {
    private String clOrdId;

    public FIXOrderEvent(String senderId, long timestamp,
                         double basePrice, String clOrdId) {
        super(senderId, timestamp, basePrice);
        this.clOrdId = clOrdId;
    }
 @Override
    public void dispatch() {
        double fee = basePrice * 0.0015;
        double totalCost = basePrice + fee;

        System.out.println("\n--- FIX Stock Purchase Order ---");
        System.out.println("Sender ID: " + senderId);
        System.out.println("Client Order ID: " + clOrdId);
        System.out.printf("Stock Price: %.2f%n", basePrice);
        System.out.printf("Exchange Fee (0.15%%): %.2f%n", fee);
        System.out.printf("Total Cost: %.2f%n", totalCost);
        System.out.println("Order dispatched successfully.");
    }
    
}

