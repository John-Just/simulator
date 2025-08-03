package net.john_just.simulator;

public class TerminalSelector {
    private static TerminalView selected = null;

    public static void handleClick(TerminalView clicked) {
        if (selected == null) {
            selected = clicked;
            clicked.setAsSelected();
        } else if (selected == clicked) {
            selected.setAsDefault(); // снятие выбора
            selected = null;
        } else {
            // соединяем
            selected.getTerminal().connectTo(clicked.getTerminal());
            selected.setAsConnected();
            clicked.setAsConnected();
            selected = null;
        }
    }
}
