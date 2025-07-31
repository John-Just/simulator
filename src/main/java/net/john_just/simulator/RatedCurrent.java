package net.john_just.simulator;

public enum RatedCurrent {
    A6(6),
    A10(10),
    A16(16),
    A20(20),
    A25(25),
    A32(32),
    A40(40),
    A63(63);

    private final int value;

    RatedCurrent(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + " А";
    }
}

