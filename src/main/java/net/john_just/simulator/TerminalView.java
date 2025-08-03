package net.john_just.simulator;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TerminalView extends Circle {
    private final Terminal terminal;

    public TerminalView(Terminal terminal) {
        super(5, Color.DARKGRAY); // начальный цвет
        this.setStroke(Color.BLACK);
        this.terminal = terminal;

        this.setOnMouseClicked(e -> {
            TerminalSelector.handleClick(this);
            e.consume();
        });
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setAsSelected() {
        this.setFill(Color.BLUE);
    }

    public void setAsConnected() {
        this.setFill(Color.LIMEGREEN);
    }

    public void setAsDefault() {
        this.setFill(Color.DARKGRAY);
    }
}
