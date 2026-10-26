package Question2;


import java.util.Scanner;

public class PacketSymmetryAuditor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter packet payload: ");
        String payload = scanner.nextLine();

        if (payload.isEmpty()) {
            System.out.println("Packet payload is empty.");
            scanner.close();
            return;
        }

        TransactionStack stack =
                new TransactionStack(payload.length());

        RequestQueue queue =
                new RequestQueue(payload.length());

        for (int i = 0; i < payload.length(); i++) {
            char c = payload.charAt(i);
            stack.push(c);
            queue.enqueue(c);
        }

        boolean isSymmetric = true;

        while (!stack.isEmpty() && !queue.isEmpty()) {
            char fromStack = stack.pop();
            char fromQueue = queue.dequeue();

            if (fromStack != fromQueue) {
                isSymmetric = false;
            }
        }

        System.out.println("\n--- Packet Audit Result ---");
        System.out.println("Payload: " + payload);

        if (isSymmetric) {
            System.out.println(
                    "Result: Payload is a palindrome.");
        } else {
            System.out.println(
                    "Result: Payload is not a palindrome.");
        }

        System.out.println("Audit completed.");
        scanner.close();
    }
}

