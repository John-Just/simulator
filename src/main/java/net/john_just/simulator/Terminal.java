package net.john_just.simulator;

import java.util.ArrayList;
import java.util.List;

public class Terminal {
    private Connection connection;
    private List<Terminal> directConnections = new ArrayList<>();
    private double voltage;

    public void connectTo(Terminal other) {
        if (!directConnections.contains(other)) {
            directConnections.add(other);
            other.directConnections.add(this);
        }

        if (this.connection != null && other.connection != null && this.connection != other.connection) {
            Connection merged = new Connection();
            merged.updateVoltage((this.getVoltage() + other.getVoltage()) / 2.0);
            for (Terminal t : this.connection.getTerminals()) merged.addTerminal(t);
            for (Terminal t : other.connection.getTerminals()) merged.addTerminal(t);
        } else if (this.connection != null) {
            this.connection.addTerminal(other);
        } else if (other.connection != null) {
            other.connection.addTerminal(this);
        } else {
            Connection conn = new Connection();
            conn.addTerminal(this);
            conn.addTerminal(other);
        }
    }

    public void disconnectFrom(Terminal other) {
        directConnections.remove(other);
        other.directConnections.remove(this);
        if (connection != null) {
            connection.removeTerminal(this);
            connection.removeTerminal(other);
        }
    }

    public void disconnect() {
        for (Terminal t : new ArrayList<>(directConnections)) {
            disconnectFrom(t);
        }
    }

    public List<Terminal> getDirectConnections() {
        return directConnections;
    }

    public double getVoltage() {
        return connection != null ? connection.getVoltage() : voltage;
    }

    public void setVoltage(double voltage) {
        if (connection != null) {
            connection.updateVoltage(voltage);
        } else {
            this.voltage = voltage;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}


