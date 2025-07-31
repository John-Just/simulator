module net.john_just.simulator {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.sql;
    requires java.desktop;
    requires jdk.jdi;

    opens net.john_just.simulator to javafx.fxml;
    exports net.john_just.simulator;
}