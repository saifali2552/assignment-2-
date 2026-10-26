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
public void enqueue(char c) {
        if (size == queue.length) {
            throw new IllegalStateException("Queue is full");
        }

        queue[rear] = c;
        rear = (rear + 1) % queue.length;
        size++;
    }
    public char dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }

        char value = queue[front];
        front = (front + 1) % queue.length;
        size--;
        return value;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}

