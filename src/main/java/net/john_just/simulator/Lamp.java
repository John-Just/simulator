package net.john_just.simulator;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Lamp extends Component {
    private final Circle bulb;
    private double smoothedBrightness = 0.0;

    public Lamp() {
        super(2); // L и N
        this.bulb = new Circle(20, Color.rgb(255, 255, 100, 0.2)); // по умолчанию тускло
    }

    @Override
    public void update(double time) {
        Terminal a = terminals.get(0);
        Terminal b = terminals.get(1);

        boolean aConnected = !a.getConnected().isEmpty();
        boolean bConnected = !b.getConnected().isEmpty();

        double u = 0.0;
        if (aConnected && bConnected) {
            u = a.getVoltage() - b.getVoltage();
        }

        double targetBrightness = Math.min(1.0, (u * u) / (230.0 * 230.0));
        smoothedBrightness += (targetBrightness - smoothedBrightness) * 0.1;

        bulb.setFill(Color.rgb(255, 255, 100, smoothedBrightness));
    }

    @Override
    public Node createView() {
        Pane root = new Pane();
        root.setPrefSize(80, 80);

        bulb.setStroke(Color.BLACK);

        // Метка
        Text label = new Text("Лампа");
        label.setFont(Font.font(10));
        label.setX(5);
        label.setY(10);
        root.getChildren().add(label);

        // Круглая лампа
        bulb.setCenterX(40);
        bulb.setCenterY(40);
        root.getChildren().add(bulb);

        // Терминалы
        TerminalView t1 = new TerminalView(getTerminals().get(0));
        t1.setLayoutX(10);
        t1.setLayoutY(70);
        root.getChildren().add(t1);

        TerminalView t2 = new TerminalView(getTerminals().get(1));
        t2.setLayoutX(60);
        t2.setLayoutY(70);
        root.getChildren().add(t2);

        return root;
    }
}
