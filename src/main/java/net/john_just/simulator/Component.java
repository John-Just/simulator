package net.john_just.simulator;

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public abstract class Component {
    protected String name;
    protected List<Terminal> terminals = new ArrayList<>();


    public Component(int terminalCount) {
        for (int i = 0; i < terminalCount; i++) {
            terminals.add(new Terminal());
        }
    }

    public abstract void update(double time);

    public List<Terminal> getTerminals() {
        return terminals;
    }

    public abstract Node createView();
}
