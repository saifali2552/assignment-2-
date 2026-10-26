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
    
    public char pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack underflow");
        }
        return stack[top--];
    }

    public char peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stack[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }
}
