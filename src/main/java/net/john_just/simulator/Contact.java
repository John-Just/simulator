package net.john_just.simulator;

public class Contact {
    private Terminal a;
    private Terminal b;
    private final ContactType type;
    private boolean closed;

    public Contact(Terminal a, Terminal b, ContactType type) {
        this.a = a;
        this.b = b;
        this.type = type;

        this.closed = (type == ContactType.NC); // замкнут сразу, если НЗ
        if (closed) {
            a.connectTo(b);
        }
    }

    public void setActivated(boolean activated) {
        // При активации: NO должен замкнуться, NC разомкнуться
        boolean shouldBeClosed = (type == ContactType.NC) != activated;

        if (shouldBeClosed != closed) {
            closed = shouldBeClosed;
            if (closed) {
                a.connectTo(b);
            } else {
                a.disconnectFrom(b);
            }
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

    public ContactType getType() {
        return type;
    }
}
