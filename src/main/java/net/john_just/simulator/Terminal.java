package net.john_just.simulator;

import java.util.ArrayList;
import java.util.List;

public class Terminal {
    private double voltage;
    private final List<Terminal> connected = new ArrayList<>();

    public Terminal() {
        voltage = 0.0;
    }

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public void connectTo(Terminal other) {
        if (!connected.contains(other)) {
            connected.add(other);
            other.connectTo(this); // двустороннее соединение
        }
    }

    public void disconnectFrom(Terminal other) {
        if (connected.contains(other)) {
            connected.remove(other);
            other.disconnectFrom(this); // двустороннее разъединение
        }
    }

    public List<Terminal> getConnected() {
        return connected;
    }
}
