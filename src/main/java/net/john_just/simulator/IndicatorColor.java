package net.john_just.simulator;

import javafx.scene.paint.Color;

public enum IndicatorColor {
    WHITE,
    RED,
    GREEN,
    BLUE,
    YELLOW;

    public Color toFxColor() {
        return switch (this) {
            case WHITE -> Color.WHITE;
            case RED -> Color.RED;
            case GREEN -> Color.LIMEGREEN; // Ярче и привычнее
            case BLUE -> Color.DEEPSKYBLUE;
            case YELLOW -> Color.GOLD;
        };
    }
}
