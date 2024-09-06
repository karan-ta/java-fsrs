package org.example;

public enum State {
    New(0),
    Learning(1),
    Review(2),
    Relearning(3);

    private final int value;

    State(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
