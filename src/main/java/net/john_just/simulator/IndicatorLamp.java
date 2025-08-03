package net.john_just.simulator;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class IndicatorLamp extends Component {

    private final IndicatorColor color;
    private final double thresholdVoltage = 15; // ниже не светится
    private final Circle bulb;

    public IndicatorLamp(IndicatorColor color) {
        super(2);
        this.color = color;
        this.bulb = new Circle(20, Color.GRAY); // по умолчанию не горит
    }

    @Override
    public Node createView() {
        Pane root = new Pane();
        root.setPrefSize(80, 80);

        bulb.setStroke(Color.BLACK);

        // Текстовая метка
        Text label = new Text("Индикатор");
        label.setFont(Font.font(10));
        label.setX(5);
        label.setY(10);
        root.getChildren().add(label);

        // Сам круг — лампа
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
        boolean isOn = Math.abs(u) >= thresholdVoltage;
        bulb.setFill(isOn ? color.toFxColor() : Color.GRAY);
    }
}
