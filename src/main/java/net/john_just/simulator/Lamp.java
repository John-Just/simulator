package net.john_just.simulator;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Lamp extends Component {
    private Rectangle bulb;

    public Lamp() {
        super(2); // L и N
    }

    @Override
    public void update(double time) {
        double u = terminals.get(0).getVoltage() - terminals.get(1).getVoltage();

        // Максимальное напряжение — 230 В, при нём максимальная яркость
        double brightness = Math.min(1.0, Math.abs(u) / 230.0);
        System.out.printf("\rBrightness: %.2f", brightness);


        if (bulb != null) {
            bulb.setFill(Color.rgb(255, 255, 100, brightness)); // жёлтый цвет с прозрачностью
        }
    }

    @Override
    public Node createView() {
        Pane root = new Pane();
        root.setPrefSize(60, 40);

        Rectangle base = new Rectangle(60, 40);
        base.setFill(Color.LIGHTGRAY);
        base.setStroke(Color.BLACK);

        bulb = new Rectangle(20, 20, Color.rgb(255, 255, 100, 0.2)); // изначально тусклая
        bulb.setLayoutX(20);
        bulb.setLayoutY(10);

        Text label = new Text("Лампа");
        label.setFont(Font.font(12));
        label.setLayoutX(10);
        label.setLayoutY(35);

        root.getChildren().addAll(base, bulb, label);

        // Клеммы: слева и справа
        for (int i = 0; i < terminals.size(); i++) {
            Circle terminalCircle = new Circle(5, Color.DARKGRAY);
            terminalCircle.setStroke(Color.BLACK);
            terminalCircle.setLayoutX(i == 0 ? 0 : 60); // L слева, N справа
            terminalCircle.setLayoutY(20);
            root.getChildren().add(terminalCircle);
        }

        Pane wrapper = new Pane(root);
        wrapper.setLayoutX(100);
        wrapper.setLayoutY(100);

        return wrapper;
    }
}

