package net.john_just.simulator;

public class Contact {
    private Terminal a;
    private Terminal b;
    private boolean closed = false;

    public Contact(Terminal a, Terminal b) {
        this.a = a;
        this.b = b;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
        if (closed) {
            a.connectTo(b);
        } else {
            a.disconnectFrom(b);
        }
    }

    public boolean isClosed() {
        return closed;
    }

    public Terminal getFirst() {
        return a;
    }

    public Terminal getSecond() {
        return b;
    }
}

