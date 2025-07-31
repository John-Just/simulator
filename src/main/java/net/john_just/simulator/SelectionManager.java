package net.john_just.simulator;

public class SelectionManager {
    private static SelectableComponent selected;

    public static void select(SelectableComponent comp) {
        if (selected != null) {
            selected.setSelected(false);
        }
        selected = comp;
        comp.setSelected(true);
    }

    public static void clear() {
        if (selected != null) {
            selected.setSelected(false);
            selected = null;
        }
    }
}
