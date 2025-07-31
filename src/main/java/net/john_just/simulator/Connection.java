package net.john_just.simulator;

import java.util.ArrayList;
import java.util.List;

public class Connection {
    private final List<Terminal> terminals = new ArrayList<>();
    private double voltage = 0.0;

    public void addTerminal(Terminal t) {
        terminals.add(t);
        t.connection = this; // <-- ВАЖНО!
    }

    public void updateVoltage(double newVoltage) {
        this.voltage = newVoltage;
        for (Terminal t : terminals) {
            t.voltage = newVoltage;
        }
    }

    public List<Terminal> getTerminals() {
        return terminals;
    }

    public double getVoltage() {
        return voltage;
    }

    public void removeTerminal(Terminal t) {
        terminals.remove(t);
        if (terminals.isEmpty()) {
            voltage = 0.0;
        }
    }
}
