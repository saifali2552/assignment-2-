package Question2;


public class TransactionStack {
    private char[] stack;
    private int top;

    public TransactionStack(int capacity) {
        stack = new char[capacity];
        top = -1;
    }

    public void push(char c) {
        if (top == stack.length - 1) {
            throw new IllegalStateException("Stack overflow");
        }
        stack[++top] = c;
    }

}
