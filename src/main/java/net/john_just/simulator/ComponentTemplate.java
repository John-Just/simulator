package net.john_just.simulator;

import javafx.scene.Node;

import java.util.function.Supplier;

public class ComponentTemplate {
    private final String name;
    private final Supplier<Node> createFunction;

    public ComponentTemplate(String name, Supplier<Node> createFunction) {
        this.name = name;
        this.createFunction = createFunction;
    }

    public String getName() {
        return name;
    }

    public Node createInstance() {
        return createFunction.get();
    }
}

