package net.john_just.simulator;

public class Terminal {
    Connection connection;

    public double voltage; // Опционально — можно убрать и всегда брать из connection

    public Terminal() {
        this.voltage = 0.0;
    }

    public void connectTo(Terminal other) {
        if (this.connection != null && other.connection != null) {
            // Объединяем сети
            if (this.connection != other.connection) {
                for (Terminal t : other.connection.getTerminals()) {
                    this.connection.addTerminal(t);
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
            connection = new Connection();
            connection.addTerminal(this);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}

