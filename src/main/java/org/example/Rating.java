package org.example;

public enum Rating {
    Again(1),
    Hard(2),
    Good(3),
    Easy(4);

    private final int value;

    Rating(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
