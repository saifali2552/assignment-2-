package Question2;


public class RequestQueue {
    private char[] queue;
    private int front;
    private int rear;
    private int size;

    public RequestQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "Capacity must be positive");
        }

        queue = new char[capacity];
        front = 0;
        rear = 0;
        size = 0;
    }

   
}

