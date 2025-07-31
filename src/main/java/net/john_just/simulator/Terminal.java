package net.john_just.simulator;

import java.util.ArrayList;

public class Terminal {
    Connection connection;

    public double voltage; // Опционально — можно убрать и всегда брать из connection

    public Terminal() {
        this.voltage = 0.0;
    }

    public void connectTo(Terminal other) {
        if (this.connection != null && other.connection != null) {
            if (this.connection != other.connection) {
                // Объединяем: переносим все из другой в эту
                Connection oldConn = other.connection;
                for (Terminal t : new ArrayList<>(oldConn.getTerminals())) {
                    this.connection.addTerminal(t); // обновляет ссылку на новую связь
                }
            }
        } else if (this.connection != null) {
            this.connection.addTerminal(other);
        } else if (other.connection != null) {
            other.connection.addTerminal(this);
        } else {
            Connection newConn = new Connection();
            newConn.addTerminal(this);
            newConn.addTerminal(other);
        }
    }


    public void setVoltage(double v) {
        if (connection != null) {
            connection.updateVoltage(v);
        } else {
            voltage = v;
        }
    }

    public double getVoltage() {
        return connection != null ? connection.getVoltage() : voltage;
    }

    public void disconnectFrom(Terminal other) {
        if (this.connection != null && this.connection == other.connection) {
            this.connection.removeTerminal(this);
            other.connection.removeTerminal(other);
            this.connection = null;
            other.connection = null;
        }
    }

    public void disconnect() {
        if (connection != null) {
            connection.removeTerminal(this);
            connection = null;
        }
    }


    public Connection getConnection() {
        return connection;
    }
}

