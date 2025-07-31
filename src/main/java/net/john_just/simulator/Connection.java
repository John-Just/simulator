package net.john_just.simulator;

import java.util.ArrayList;
import java.util.List;

public class Connection {
    private double voltage = 0.0;
    private final List<Terminal> terminals = new ArrayList<>();

    public void addTerminal(Terminal terminal) {
        if (!terminals.contains(terminal)) {
            terminals.add(terminal);
            terminal.setConnection(this);
        }
    }

    public void removeTerminal(Terminal terminal) {
        terminals.remove(terminal);
        if (terminal.getConnection() == this) {
            terminal.setConnection(null);
        }
    }

    public List<Terminal> getTerminals() {
        return terminals;
    }

    public double getVoltage() {
        return voltage;
    }

    public void updateVoltage(double voltage) {
        this.voltage = voltage;
        for (Terminal terminal : terminals) {
            // можно будет обновить компонент, если надо
        }
    }
}
